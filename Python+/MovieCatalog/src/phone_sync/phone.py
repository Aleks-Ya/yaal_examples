"""Finding the phone and changing files on it through `gio` (GVFS over MTP).

Plain file writes on the GVFS FUSE mount are unreliable for MTP, so every change
goes through `gio`. Reading (listing, sizes) uses the FUSE mount directly.
"""

import os
import subprocess
from collections.abc import Callable
from pathlib import Path

STORAGE = "Internal storage"
PROBE = ".sync-phone-probe"

NOT_FOUND = (
    "Phone not found. Connect it by USB, unlock it, and choose 'File transfer' "
    "in the USB notification."
)
NO_TRANSFER = (
    "Phone is connected, but file transfer is not enabled. Unlock the phone and "
    "choose 'File transfer' in the USB notification."
)
READ_ONLY = "Phone storage is read-only."


class PhoneError(Exception):
    """The phone is missing or unusable. The message is shown to the user as is."""


class GioError(Exception):
    """A `gio` command failed."""


Runner = Callable[..., subprocess.CompletedProcess]


def default_gvfs_root() -> Path:
    return Path(f"/run/user/{os.getuid()}/gvfs")


def find_movies_dir(gvfs_root: Path) -> Path:
    """Return the phone's `Movies` folder (it may not exist yet)."""
    hosts = sorted(gvfs_root.glob("mtp:host=*"))
    if not hosts:
        raise PhoneError(NOT_FOUND)
    if len(hosts) > 1:
        listed = "\n".join(f"  {h}" for h in hosts)
        raise PhoneError(
            f"Several MTP devices are connected:\n{listed}\n"
            f"Choose one with --phone '<device>/{STORAGE}/Movies'."
        )
    storage = hosts[0] / STORAGE
    try:
        entries = os.listdir(storage)
    except OSError:
        raise PhoneError(NO_TRANSFER) from None
    if not entries:
        raise PhoneError(NO_TRANSFER)
    return storage / "Movies"


class Gio:
    def __init__(self, run: Runner = subprocess.run):
        self._run = run

    def _call(self, *args, show_output: bool = False, input: str | None = None) -> str:
        """Run gio; stderr is always captured for error messages."""
        cmd = ["gio", *(str(a) for a in args)]
        stdout = None if show_output else subprocess.PIPE
        try:
            result = self._run(cmd, stdout=stdout, stderr=subprocess.PIPE, text=True, input=input)
        except FileNotFoundError:
            raise GioError("the 'gio' command is not installed") from None
        if result.returncode != 0:
            message = (result.stderr or "").strip()
            raise GioError(message or f"gio {args[0]} failed with exit code {result.returncode}")
        return result.stdout or ""

    def fs_info(self, path: Path) -> dict[str, str]:
        info = {}
        for line in self._call("info", "-f", path).splitlines():
            key, sep, value = line.strip().partition(": ")
            if sep and key.startswith("filesystem::"):
                info[key.removeprefix("filesystem::")] = value
        return info

    def free_space(self, path: Path) -> int:
        return int(self.fs_info(path)["free"])

    def is_readonly(self, path: Path) -> bool:
        return self.fs_info(path).get("readonly", "FALSE").upper() == "TRUE"

    def mkdir(self, path: Path) -> None:
        self._call("mkdir", "-p", path)

    def copy(self, src: Path, dst: Path) -> None:
        """Copy a file, showing gio's progress line on the terminal."""
        self._call("copy", "-p", src, dst, show_output=True)

    def move_into(self, src: Path, dst_dir: Path) -> None:
        """Move into an existing folder; never falls back to copy-and-delete."""
        self._call("move", "--no-copy-fallback", src, dst_dir)

    def rename(self, path: Path, new_name: str) -> None:
        self._call("rename", path, new_name)

    def remove(self, path: Path) -> None:
        self._call("remove", path)

    def create_empty(self, path: Path) -> None:
        self._call("save", path, input="")

    def remove_tree(self, path: Path) -> None:
        for dirpath, dirnames, filenames in os.walk(path, topdown=False):
            for name in filenames:
                self.remove(Path(dirpath, name))
            for name in dirnames:
                self.remove(Path(dirpath, name))
        self.remove(path)


def check_writable(gio: Gio, movies_dir: Path) -> None:
    """Fail with a clear message unless a file can be created next to `Movies`."""
    probe_dir = movies_dir if movies_dir.is_dir() else movies_dir.parent
    try:
        if gio.is_readonly(probe_dir):
            raise PhoneError(READ_ONLY)
        probe = probe_dir / PROBE
        gio.create_empty(probe)
        gio.remove(probe)
    except GioError as e:
        raise PhoneError(f"Phone storage is not writable: {e}") from e

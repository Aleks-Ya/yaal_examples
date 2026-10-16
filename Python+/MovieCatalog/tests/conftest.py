import shutil
import subprocess
from pathlib import Path

import pytest


def make_tree(root: Path, files: dict[str, int | str]) -> Path:
    """Create files under root: an int is a size in bytes, a str is the content."""
    for rel, content in files.items():
        path = root / rel
        path.parent.mkdir(parents=True, exist_ok=True)
        if isinstance(content, int):
            path.write_bytes(b"x" * content)
        else:
            path.write_text(content, encoding="utf-8")
    return root


class FakeGio:
    """Stands in for subprocess.run: carries out `gio` commands on local files."""

    def __init__(self, free: int = 10**12, readonly: bool = False):
        self.free = free
        self.readonly = readonly
        self.calls: list[list[str]] = []
        self.fail: dict[str, str] = {}  # subcommand -> stderr to fail with

    def commands(self, name: str) -> list[list[str]]:
        return [c for c in self.calls if c[1] == name]

    def __call__(self, cmd, input=None, **kwargs):
        assert cmd[0] == "gio"
        self.calls.append(cmd)
        sub, args = cmd[1], cmd[2:]
        if sub in self.fail:
            return subprocess.CompletedProcess(cmd, 1, "", self.fail[sub])
        out = ""
        match sub:
            case "info":
                ro = "TRUE" if self.readonly else "FALSE"
                out = (f"attributes:\n  filesystem::free: {self.free}\n"
                       f"  filesystem::readonly: {ro}\n")
            case "mkdir":
                Path(args[-1]).mkdir(parents=True, exist_ok=True)
            case "copy":
                src, dst = args[-2:]
                shutil.copyfile(src, dst)
            case "move":
                src, dst = Path(args[-2]), Path(args[-1])
                shutil.move(src, dst / src.name if dst.is_dir() else dst)
            case "rename":
                path = Path(args[0])
                path.rename(path.with_name(args[1]))
            case "remove":
                path = Path(args[0])
                path.rmdir() if path.is_dir() else path.unlink()
            case "save":
                Path(args[0]).write_text(input or "")
            case _:
                raise AssertionError(f"unexpected gio command {cmd}")
        return subprocess.CompletedProcess(cmd, 0, out, "")


@pytest.fixture
def fake_gio() -> FakeGio:
    return FakeGio()

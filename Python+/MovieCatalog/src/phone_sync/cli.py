"""sync-phone: copy the movie library from the drive to the phone.

Nothing is deleted from the phone unless --prune is given and confirmed.
"""

import argparse
import os
import subprocess
import sys
from collections import defaultdict
from collections.abc import Callable
from pathlib import Path

from .phone import (
    Gio,
    GioError,
    PhoneError,
    check_writable,
    default_gvfs_root,
    find_movies_dir,
)
from .planner import Plan, build_plan
from .rules import FORBIDDEN_CHARS, MARKER
from .scan import CATEGORIES, scan_library

DEFAULT_DRIVE = Path("/media/aleks/ADATA/movies/Movies")
GB = 1_000_000_000


def fmt_size(n: int) -> str:
    if abs(n) >= GB:
        return f"{n / GB:.1f} GB"
    return f"{n / 1_000_000:.1f} MB"


def parse_args(argv: list[str] | None) -> argparse.Namespace:
    p = argparse.ArgumentParser(
        prog="sync-phone",
        description=(
            "Copy titles from the drive's Movies/ to the phone's Movies/. "
            f"Put a '{MARKER}' file in a title folder to keep it off the phone; "
            "lines in it are glob patterns that exclude only matching files."
        ),
    )
    p.add_argument("--dry-run", action="store_true", help="show the plan, change nothing")
    p.add_argument("-y", "--yes", action="store_true", help="copy without asking")
    p.add_argument("--reserve", type=float, default=10, metavar="GB",
                   help="free space to leave on the phone (default: 10)")
    p.add_argument("--prune", action="store_true",
                   help="offer to delete phone items that are not (or no longer) wanted")
    p.add_argument("--drive", type=Path, default=DEFAULT_DRIVE,
                   help=f"the drive's Movies folder (default: {DEFAULT_DRIVE})")
    p.add_argument("--phone", type=Path,
                   help="the phone's Movies folder (default: found automatically)")
    return p.parse_args(argv)


def format_summary(plan: Plan, free: int, reserve: int, missing_dirs: list[str]) -> str:
    lines: list[str] = []

    def section(title: str, items: list[str]) -> None:
        if items:
            lines.append(f"{title}:")
            lines.extend(f"  {item}" for item in items)
            lines.append("")

    section("Create on phone", [f"{d}/" for d in missing_dirs])
    section("Rename/move on phone", [f"{m.src}  ->  {m.dst}" for m in plan.moves])
    section("Move within title on phone",
            [f"{m.title}: {m.src}  ->  {m.dst}" for m in plan.file_moves])

    by_title: dict[str, list] = defaultdict(list)
    for c in plan.copies:
        by_title[c.title].append(c)
    copy_lines = []
    for title, copies in by_title.items():
        size = sum(c.size for c in copies)
        kind = "update" if any(c.old_size is not None for c in copies) else "copy"
        files = "file" if len(copies) == 1 else "files"
        copy_lines.append(f"{title}  ({len(copies)} {files}, {fmt_size(size)}, {kind})")
    section("Copy to phone", copy_lines)

    section("Not copied: name has a character Android rejects (" + " ".join(sorted(FORBIDDEN_CHARS))
            + "); rename it on the drive", plan.bad_names)
    section(f"Skipped ({MARKER})", plan.skipped_titles)
    section(f"Skipped files ({MARKER} patterns)",
            [f"{t}/{rel}  ({fmt_size(s)})" for t, rel, s in plan.skipped_files])
    section("On the phone but not wanted (kept; use --prune to delete)", plan.extras)

    after = plan.free_after(free)
    lines.append(f"To copy:        {fmt_size(plan.bytes_to_copy)} in {len(plan.copies)} files")
    lines.append(f"Phone free now: {fmt_size(free)}")
    lines.append(f"Free after:     {fmt_size(after)}  (reserve {fmt_size(reserve)})")
    return "\n".join(lines)


def confirm(ask: Callable[[str], str], question: str) -> bool:
    try:
        return ask(f"{question} [y/N] ").strip().lower() in ("y", "yes")
    except EOFError:
        return False


def execute(plan: Plan, gio: Gio, drive: Path, phone: Path,
            missing_dirs: list[str]) -> list[tuple[str, str]]:
    """Apply the plan. Returns (item, error) for everything that failed."""
    failures: list[tuple[str, str]] = []
    failed_titles: set[str] = set()

    for d in missing_dirs:
        try:
            gio.mkdir(phone / d)
        except GioError as e:
            failures.append((f"{d}/", str(e)))

    for m in plan.moves:
        src_category, src_name = m.src.split("/", 1)
        dst_category, dst_name = m.dst.split("/", 1)
        print(f"move   {m.src}  ->  {m.dst}")
        try:
            path = phone / m.src
            if src_category != dst_category:
                gio.move_into(path, phone / dst_category)
                path = phone / dst_category / src_name
            if src_name != dst_name:
                gio.rename(path, dst_name)
        except GioError as e:
            failures.append((m.src, str(e)))
            failed_titles.add(m.dst)

    made_dirs: set[Path] = set()
    for m in plan.file_moves:
        if m.title in failed_titles:
            continue
        title_dir = phone / m.title
        src, dst = title_dir / m.src, title_dir / m.dst
        print(f"move   {m.title}/{m.src}  ->  {m.dst}")
        try:
            if dst.parent not in made_dirs:
                gio.mkdir(dst.parent)
                made_dirs.add(dst.parent)
            gio.move_into(src, dst.parent)
            # Remove the folders the move left empty, e.g. a release subfolder.
            folder = src.parent
            while folder != title_dir and not os.listdir(folder):
                gio.remove(folder)
                folder = folder.parent
        except GioError as e:
            # Not copied instead, which would leave two copies; the next run retries the move.
            failures.append((f"{m.title}/{m.src}", str(e)))

    copies = [c for c in plan.copies if c.title not in failed_titles]
    for i, c in enumerate(copies, 1):
        target = phone / c.title / c.rel
        partial = target.with_name(target.name + ".partial")
        print(f"[{i}/{len(copies)}] {c.title}/{c.rel}  ({fmt_size(c.size)})")
        try:
            if target.parent not in made_dirs:
                gio.mkdir(target.parent)
                made_dirs.add(target.parent)
            if partial.exists():
                gio.remove(partial)
            # Copy under a temporary name, so an interrupted copy never looks complete.
            gio.copy(drive / c.title / c.rel, partial)
            if c.old_size is not None:
                gio.remove(target)
            gio.rename(partial, target.name)
        except GioError as e:
            failures.append((f"{c.title}/{c.rel}", str(e)))
    return failures


def prune(plan: Plan, gio: Gio, phone: Path,
          ask: Callable[[str], str]) -> list[tuple[str, str]]:
    if not plan.extras:
        return []
    print("\nNot wanted on the phone:")
    for item in plan.extras:
        print(f"  {item}")
    if not confirm(ask, f"Delete these {len(plan.extras)} items from the phone?"):
        print("Nothing deleted.")
        return []
    failures = []
    for item in plan.extras:
        path = phone / item
        try:
            if path.is_dir():
                gio.remove_tree(path)
            else:
                gio.remove(path)
            print(f"deleted {item}")
        except GioError as e:
            failures.append((item, str(e)))
    return failures


def main(argv: list[str] | None = None, *, run=subprocess.run,
         gvfs_root: Path | None = None, ask: Callable[[str], str] = input) -> int:
    args = parse_args(argv)
    gio = Gio(run)
    reserve = int(args.reserve * GB)

    if not args.drive.is_dir():
        print(f"Drive library not found: {args.drive}", file=sys.stderr)
        return 2

    try:
        phone = args.phone or find_movies_dir(gvfs_root or default_gvfs_root())
        if not phone.parent.is_dir():
            raise PhoneError(f"Phone folder not found: {phone.parent}")
        check_writable(gio, phone)
        free = gio.free_space(phone if phone.is_dir() else phone.parent)
    except (PhoneError, GioError) as e:
        print(e, file=sys.stderr)
        return 2

    print(f"Drive: {args.drive}\nPhone: {phone}\nScanning...\n")
    plan = build_plan(scan_library(args.drive), scan_library(phone))
    missing_dirs = [c for c in CATEGORIES if not (phone / c).is_dir()]
    if not plan.has_work:
        missing_dirs = []

    print(format_summary(plan, free, reserve, missing_dirs))

    if not plan.has_work:
        print("\nPhone is up to date.")
    elif plan.free_after(free) < reserve:
        print("\nNot enough space on the phone. Add a .no-phone marker to some titles, "
              "or lower --reserve.", file=sys.stderr)
        return 1

    if args.dry_run:
        print("\nDry run: nothing was changed.")
        return 0

    failures: list[tuple[str, str]] = []
    if plan.has_work:
        if not (args.yes or confirm(ask, "\nProceed?")):
            print("Cancelled.")
            return 1
        failures += execute(plan, gio, args.drive, phone, missing_dirs)
    if args.prune:
        failures += prune(plan, gio, phone, ask)

    if failures:
        print(f"\n{len(failures)} failed:", file=sys.stderr)
        for item, error in failures:
            print(f"  {item}: {error}", file=sys.stderr)
        return 1
    if plan.has_work:
        print("\nDone.")
    return 0


def run_cli() -> None:
    # Show progress promptly even when output goes to a file or a pipe.
    sys.stdout.reconfigure(line_buffering=True)
    try:
        sys.exit(main())
    except KeyboardInterrupt:
        print("\nInterrupted. Run again to finish; partial copies are redone.", file=sys.stderr)
        sys.exit(130)

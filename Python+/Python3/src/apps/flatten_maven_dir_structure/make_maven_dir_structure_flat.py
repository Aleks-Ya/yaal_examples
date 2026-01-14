#!/usr/bin/env python3
"""
Flatten SBT directory layout for a multi-module project.

Transforms (per module/project directory):
  src/main/scala      -> src
  src/test/scala      -> test
  src/main/resources  -> resources
  src/test/resources  -> resourcesTest

It traverses the given base directory and applies the transformation
to every directory that contains any of the source paths above.

Behavior:
- Moves files (not copies).
- Merges into existing target directories.
- Refuses to overwrite if a target file already exists.

Usage:
  python3 sbt_flatten_layout.py /path/to/project --dry-run
  python3 sbt_flatten_layout.py /path/to/project
"""

from __future__ import annotations

import argparse
import os
from pathlib import Path
from typing import Iterable, List, Tuple

MAPPINGS: List[Tuple[Path, str]] = [
    (Path("src/main/scala"), "src"),
    (Path("src/test/scala"), "test"),
    (Path("src/main/resources"), "resources"),
    (Path("src/test/resources"), "resourcesTest"),
]


def is_hidden_path(p: Path) -> bool:
    # Exclude .git, .idea, target, etc. during scanning to reduce noise
    parts = p.parts
    return any(part.startswith(".") for part in parts) or "target" in parts


def find_candidate_project_dirs(base: Path) -> List[Path]:
    """
    Return directories that appear to be SBT project/module roots
    by presence of any of the mapped source directories.
    """
    candidates = set()

    # Walk directories; avoid hidden/target to speed up.
    for root, dirs, _files in os.walk(base):
        root_path = Path(root)

        # Prune walk in-place
        pruned = []
        for d in list(dirs):
            dp = root_path / d
            if is_hidden_path(dp):
                pruned.append(d)
        for d in pruned:
            dirs.remove(d)

        # Check if root contains any mapped source dir
        for src_rel, _dst_name in MAPPINGS:
            if (root_path / src_rel).is_dir():
                candidates.add(root_path)
                break

    return sorted(candidates)


def iter_files_recursive(src_dir: Path) -> Iterable[Path]:
    for p in src_dir.rglob("*"):
        if p.is_file():
            yield p


def move_tree_merge(src_dir: Path, dst_dir: Path, dry_run: bool) -> None:
    """
    Move all files from src_dir into dst_dir, preserving relative paths.
    Refuses to overwrite existing files.
    """
    if not src_dir.exists():
        return

    for src_file in iter_files_recursive(src_dir):
        rel = src_file.relative_to(src_dir)
        dst_file = dst_dir / rel

        if dst_file.exists():
            raise FileExistsError(
                f"Refusing to overwrite existing file:\n"
                f"  source: {src_file}\n"
                f"  target: {dst_file}\n"
                f"Resolve the conflict and re-run."
            )

        if dry_run:
            print(f"[DRY] MOVE {src_file} -> {dst_file}")
            continue

        dst_file.parent.mkdir(parents=True, exist_ok=True)
        src_file.rename(dst_file)

    # Clean up empty directories after moving files
    if not dry_run:
        # Remove empty directories bottom-up
        for p in sorted(src_dir.rglob("*"), reverse=True):
            if p.is_dir():
                try:
                    p.rmdir()
                except OSError:
                    pass
        try:
            src_dir.rmdir()
        except OSError:
            pass


def flatten_project_dir(project_dir: Path, dry_run: bool) -> None:
    """
    Apply the layout transformation inside a single project/module dir.
    """
    print(f"\n== Project: {project_dir} ==")
    any_work = False

    for src_rel, dst_name in MAPPINGS:
        src_dir = project_dir / src_rel
        if not src_dir.is_dir():
            continue

        dst_dir = project_dir / dst_name
        any_work = True

        print(f"Mapping: {src_rel.as_posix()} -> {dst_name}")
        if dry_run:
            print(f"[DRY] Ensure dir exists: {dst_dir}")
        else:
            dst_dir.mkdir(parents=True, exist_ok=True)

        move_tree_merge(src_dir, dst_dir, dry_run=dry_run)

    if not any_work:
        print("No default SBT source/resource directories found here; skipping.")


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Flatten SBT default directory structure in all modules."
    )
    parser.add_argument(
        "base_dir",
        type=str,
        help="Base directory of the SBT project (root of the repo).",
    )
    parser.add_argument(
        "--dry-run",
        action="store_true",
        help="Print planned operations without making changes.",
    )
    args = parser.parse_args()

    base = Path(args.base_dir).expanduser().resolve()
    if not base.is_dir():
        print(f"ERROR: Not a directory: {base}")
        return 2

    candidates = find_candidate_project_dirs(base)
    if not candidates:
        print("No projects/modules found containing default SBT source/resource dirs.")
        return 0

    print(f"Base: {base}")
    print(f"Found {len(candidates)} project/module director{'y' if len(candidates) == 1 else 'ies'} to process.")
    for p in candidates:
        flatten_project_dir(p, dry_run=args.dry_run)

    print("\nDone.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

"""Read the title folders of a library root (the drive's or the phone's `Movies/`)."""

import os
from dataclasses import dataclass, field
from pathlib import Path

from .rules import MARKER

CATEGORIES = ("English", "Russian")

# TagSpaces metadata folders; not library content.
IGNORED_DIRS = {".ts"}


@dataclass
class Title:
    key: str  # "English/<title folder>"
    files: dict[str, int] = field(default_factory=dict)  # path inside title -> size
    marker: str | None = None  # contents of `.no-phone`, None when absent


def scan_library(root: Path) -> dict[str, Title]:
    """Map "Category/Title" to its files. Missing category folders are skipped."""
    titles: dict[str, Title] = {}
    for category in CATEGORIES:
        category_dir = root / category
        if not category_dir.is_dir():
            continue
        for entry in sorted(category_dir.iterdir()):
            if entry.name.startswith(".") or not entry.is_dir():
                continue
            title = Title(key=f"{category}/{entry.name}")
            for dirpath, dirnames, filenames in os.walk(entry):
                dirnames[:] = sorted(d for d in dirnames if d not in IGNORED_DIRS)
                for name in filenames:
                    path = Path(dirpath, name)
                    rel = path.relative_to(entry).as_posix()
                    if rel == MARKER:
                        title.marker = path.read_text(encoding="utf-8", errors="replace")
                    else:
                        title.files[rel] = path.stat().st_size
            titles[title.key] = title
    return titles

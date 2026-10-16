"""Which drive files go to the phone.

A title folder can hold a `.no-phone` marker file:
- empty (or only comments): the whole title stays off the phone;
- with lines: each line is a glob pattern, and only matching files stay off the
  phone. A pattern is matched against the path inside the title folder
  (`Season 03/x.avi`) and against the bare file name, so `*.mkv` matches
  nested files too.
"""

from fnmatch import fnmatch, fnmatchcase

MARKER = ".no-phone"

# Characters Android storage rejects: a folder with one in its name can be created
# over MTP, but no file can be stored inside it.
FORBIDDEN_CHARS = frozenset(':"*<>?\\|')

# Files that are never useful on the phone.
DEFAULT_SKIP = ("*.torrent", "*.nfo")


def parse_marker(text: str) -> list[str]:
    """Return the patterns in a marker file; an empty list means the whole title."""
    patterns = []
    for line in text.splitlines():
        line = line.strip()
        if line and not line.startswith("#"):
            patterns.append(line)
    return patterns


def _basename(rel: str) -> str:
    return rel.rsplit("/", 1)[-1]


def matches(rel: str, patterns: list[str]) -> bool:
    name = _basename(rel)
    return any(fnmatchcase(rel, p) or fnmatchcase(name, p) for p in patterns)


def has_forbidden_chars(path: str) -> bool:
    return not FORBIDDEN_CHARS.isdisjoint(path)


def is_default_skipped(rel: str) -> bool:
    name = _basename(rel).lower()
    return any(fnmatch(name, p) for p in DEFAULT_SKIP)

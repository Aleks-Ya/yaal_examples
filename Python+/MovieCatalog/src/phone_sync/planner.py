"""Compare the drive with the phone and decide what to do. No I/O here."""

from collections import Counter
from dataclasses import dataclass, field

from .rules import has_forbidden_chars, is_default_skipped, matches, parse_marker
from .scan import Title


@dataclass(frozen=True)
class Copy:
    title: str
    rel: str
    size: int
    old_size: int | None = None  # size of the phone file it replaces, if any


@dataclass(frozen=True)
class Move:
    src: str  # phone title key, e.g. "English/Old name"
    dst: str  # drive title key, e.g. "English/New name"


@dataclass(frozen=True)
class FileMove:
    title: str
    src: str  # path inside the title on the phone, e.g. "Release/release.mkv"
    dst: str  # path inside the title on the drive, e.g. "release.mkv"


@dataclass
class Plan:
    moves: list[Move] = field(default_factory=list)
    file_moves: list[FileMove] = field(default_factory=list)
    copies: list[Copy] = field(default_factory=list)
    skipped_titles: list[str] = field(default_factory=list)  # whole title has `.no-phone`
    skipped_files: list[tuple[str, str, int]] = field(default_factory=list)  # (title, rel, size)
    extras: list[str] = field(default_factory=list)  # on the phone only: "Cat/Title" or "Cat/Title/rel"
    bad_names: list[str] = field(default_factory=list)  # names Android rejects; not copied

    @property
    def bytes_to_copy(self) -> int:
        return sum(c.size for c in self.copies)

    @property
    def bytes_freed(self) -> int:
        return sum(c.old_size or 0 for c in self.copies)

    def free_after(self, free: int) -> int:
        return free - self.bytes_to_copy + self.bytes_freed

    @property
    def has_work(self) -> bool:
        return bool(self.moves or self.file_moves or self.copies)


def _wanted_files(title: Title, plan: Plan) -> dict[str, int] | None:
    """Files of a drive title that belong on the phone; None if the whole title is excluded."""
    patterns: list[str] = []
    if title.marker is not None:
        patterns = parse_marker(title.marker)
        if not patterns:
            plan.skipped_titles.append(title.key)
            return None
    if has_forbidden_chars(title.key):
        plan.bad_names.append(title.key)
        return None
    files = {}
    for rel, size in sorted(title.files.items()):
        if is_default_skipped(rel):
            continue
        if patterns and matches(rel, patterns):
            plan.skipped_files.append((title.key, rel, size))
            continue
        if has_forbidden_chars(rel):
            plan.bad_names.append(f"{title.key}/{rel}")
            continue
        files[rel] = size
    return files


def _signature(files: dict[str, int]) -> tuple[str, int] | None:
    """The largest file (normally the video) as (file name, size)."""
    if not files:
        return None
    rel, size = max(files.items(), key=lambda item: (item[1], item[0]))
    return rel.rsplit("/", 1)[-1], size


def _has_file(files: dict[str, int], signature: tuple[str, int]) -> bool:
    name, size = signature
    return any(r.rsplit("/", 1)[-1] == name and s == size for r, s in files.items())


def build_plan(drive: dict[str, Title], phone: dict[str, Title]) -> Plan:
    plan = Plan()

    wanted: dict[str, dict[str, int]] = {}
    for key in sorted(drive):
        files = _wanted_files(drive[key], plan)
        if files is not None:
            wanted[key] = files

    phone_files = {key: dict(t.files) for key, t in phone.items()}

    # A phone folder that is gone from the drive but holds the same video as a
    # drive title missing from the phone was renamed or moved: follow it
    # instead of copying the video again.
    missing = [key for key in wanted if key not in phone_files]
    orphans = sorted(key for key in phone_files if key not in drive)
    for orphan in orphans:
        signature = _signature(phone_files[orphan])
        if signature is None:
            continue
        candidates = [key for key in missing if _has_file(wanted[key], signature)]
        if len(candidates) != 1:
            continue
        target = candidates[0]
        plan.moves.append(Move(orphan, target))
        phone_files[target] = phone_files.pop(orphan)
        missing.remove(target)

    for key, files in wanted.items():
        have = phone_files.get(key, {})
        # A file moved inside the title folder (same name and size, at a path the
        # drive no longer has) is moved on the phone too, not copied again.
        for rel, size in files.items():
            if rel in have:
                continue
            name = rel.rsplit("/", 1)[-1]
            sources = [r for r, s in have.items()
                       if r not in files and s == size and r.rsplit("/", 1)[-1] == name]
            if len(sources) == 1:
                plan.file_moves.append(FileMove(key, sources[0], rel))
                have[rel] = have.pop(sources[0])
        for rel, size in files.items():
            old = have.get(rel)
            if old != size:
                plan.copies.append(Copy(key, rel, size, old))
        if key in phone_files:
            plan.extras.extend(f"{key}/{rel}" for rel in sorted(set(have) - set(files)))

    plan.extras.extend(key for key in sorted(phone_files) if key not in wanted)

    # Smallest titles first, so a long run delivers as many titles as possible early.
    totals = Counter()
    for c in plan.copies:
        totals[c.title] += c.size
    plan.copies.sort(key=lambda c: (totals[c.title], c.title, c.rel))
    return plan

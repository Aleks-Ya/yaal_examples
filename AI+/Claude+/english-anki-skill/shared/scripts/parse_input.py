#!/usr/bin/env python3
"""Validate and parse add-english-word-to-anki input.

The argument is either a single input file, or a folder of input files. In a
folder, only regular ``.md``/``.txt`` files (non-recursive, dotfiles ignored)
are read, sorted by name; files with no non-blank lines are skipped as empty.

Each file's name (without extension) is the *source* for every word in it.
Each non-empty line must contain exactly one word/phrase marked with
underscores, e.g.: Just _pin_ a medal to me body.

On success, prints a JSON object
``{"entries": [...], "skipped": [{"file": ..., "reason": "empty"}]}`` to stdout
and exits 0. Each entry is ``{"source", "file", "line", "word", "sentence"}``
(sentence has the underscores stripped).
On failure, prints one error per invalid line (prefixed with the file name) to
stderr and exits 1. A bad path exits 2.
"""
import json
import os
import re
import sys

MARKER_RE = re.compile(r"_(.+?)_")
INPUT_EXTENSIONS = (".md", ".txt")


def parse(path):
    """Parse a single input file. Returns (entries, errors).

    Each entry carries its source (file name without extension) and file
    (base name), so callers can group results by originating file.
    """
    entries = []
    errors = []
    file_name = os.path.basename(path)
    source = os.path.splitext(file_name)[0]
    with open(path, encoding="utf-8") as f:
        for line_no, raw_line in enumerate(f, start=1):
            line = raw_line.strip()
            if not line:
                continue
            matches = MARKER_RE.findall(line)
            if len(matches) == 0:
                errors.append(f"{file_name} line {line_no}: no word marked with _..._: {line}")
                continue
            if len(matches) > 1:
                errors.append(
                    f"{file_name} line {line_no}: multiple words marked with _..._ "
                    f"({', '.join(matches)}): {line}"
                )
                continue
            word = matches[0]
            sentence = MARKER_RE.sub(lambda m: m.group(1), line, count=1)
            entries.append(
                {
                    "source": source,
                    "file": file_name,
                    "line": line_no,
                    "word": word,
                    "sentence": sentence,
                }
            )
    return entries, errors


def _is_empty(path):
    """True if the file has no non-blank lines."""
    with open(path, encoding="utf-8") as f:
        return not any(line.strip() for line in f)


def input_files(path):
    """List the input files for a path (a single file, or a folder's contents).

    A folder yields its regular ``.md``/``.txt`` files (non-recursive, dotfiles
    excluded), sorted by name.
    """
    if os.path.isfile(path):
        return [path]
    return sorted(
        os.path.join(path, name)
        for name in os.listdir(path)
        if not name.startswith(".")
        and name.lower().endswith(INPUT_EXTENSIONS)
        and os.path.isfile(os.path.join(path, name))
    )


def collect(path):
    """Parse a file or folder. Returns (entries, skipped, errors).

    ``skipped`` lists empty files as ``{"file", "reason": "empty"}``.
    """
    entries = []
    skipped = []
    errors = []
    for file_path in input_files(path):
        if _is_empty(file_path):
            skipped.append({"file": os.path.basename(file_path), "reason": "empty"})
            continue
        file_entries, file_errors = parse(file_path)
        entries.extend(file_entries)
        errors.extend(file_errors)
    return entries, skipped, errors


def main():
    if len(sys.argv) != 2:
        print("usage: parse_input.py <path/to/input-file-or-folder>", file=sys.stderr)
        sys.exit(2)

    path = sys.argv[1]
    if not os.path.isfile(path) and not os.path.isdir(path):
        print(f"not a file or folder: {path}", file=sys.stderr)
        sys.exit(2)

    entries, skipped, errors = collect(path)

    if errors:
        for error in errors:
            print(error, file=sys.stderr)
        sys.exit(1)

    print(json.dumps({"entries": entries, "skipped": skipped}, ensure_ascii=False, indent=2))


if __name__ == "__main__":
    main()

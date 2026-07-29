#!/usr/bin/env python3
"""Validate and parse add-english-word-to-anki input, and clear imported lines.

The argument is a single Markdown/plain-text file. H1 headers (``# Source``)
delimit sources: every sentence under a header belongs to that source until the
next header. The special header ``# NO_SOURCE`` marks a source-less section — its
sentences are still processed, but the source is reported as ``null`` (not
mentioned in fields or tags). Empty sections (a header with no sentences, e.g.
``# Python``) are allowed. A non-blank sentence before the first header is an
error.

A sentence line marks the new word/phrase with underscores, e.g.: Just _pin_ a
medal to me body. A non-blank line under a header with **no** ``_..._`` marker
is not a sentence to import — it is silently skipped (no entry, no error) and
stays in the file untouched. A line with **more than one** marker is still an
error (which word is the new one is genuinely ambiguous).

Parse mode (``parse_input.py <file>``): on success, prints a JSON object
``{"entries": [...]}`` to stdout and exits 0. Each entry is
``{"source", "line", "word", "sentence"}`` (``source`` is ``null`` for a
``# NO_SOURCE`` section; ``sentence`` has the underscores stripped). On failure,
prints one error per invalid line to stderr and exits 1. A bad path exits 2.

Clear mode (``parse_input.py --clear <file>``): reads ``{"remove_lines": [...]}``
(1-indexed line numbers) from stdin, deletes exactly those lines from the file
(every other line, headers included, is kept verbatim), writes it back, and
prints ``{"removed": <n>}``. Line-number based, so headers are never touched (the
caller only ever passes sentence-line numbers).
"""
import json
import os
import re
import sys

MARKER_RE = re.compile(r"_(.+?)_")
HEADER_RE = re.compile(r"^#[ \t]+(.*\S)\s*$")
NO_SOURCE = "NO_SOURCE"


def parse(path):
    """Parse the input file. Returns (entries, errors).

    Each entry carries the source of its section (the H1 header text, or ``None``
    for a ``# NO_SOURCE`` section).
    """
    entries = []
    errors = []
    source = None
    have_header = False
    with open(path, encoding="utf-8") as f:
        for line_no, raw_line in enumerate(f, start=1):
            line = raw_line.strip()
            if not line:
                continue
            if line.startswith("#"):
                match = HEADER_RE.match(line)
                if not match:
                    errors.append(
                        f"line {line_no}: malformed source header (use '# Source'): {line}"
                    )
                    continue
                title = match.group(1).strip()
                source = None if title == NO_SOURCE else title
                have_header = True
                continue
            if not have_header:
                errors.append(
                    f"line {line_no}: sentence before any '# source' header: {line}"
                )
                continue
            matches = MARKER_RE.findall(line)
            if len(matches) == 0:
                continue
            if len(matches) > 1:
                errors.append(
                    f"line {line_no}: multiple words marked with _..._ "
                    f"({', '.join(matches)}): {line}"
                )
                continue
            word = matches[0]
            sentence = MARKER_RE.sub(lambda m: m.group(1), line, count=1)
            entries.append(
                {
                    "source": source,
                    "line": line_no,
                    "word": word,
                    "sentence": sentence,
                }
            )
    return entries, errors


def clear(path, remove_lines):
    """Delete the given 1-indexed lines from the file. Returns the count removed."""
    remove = set(remove_lines)
    with open(path, encoding="utf-8") as f:
        lines = f.readlines()
    kept = [line for line_no, line in enumerate(lines, start=1) if line_no not in remove]
    with open(path, "w", encoding="utf-8") as f:
        f.writelines(kept)
    return len(remove_lines)


def main():
    args = sys.argv[1:]
    if args and args[0] == "--clear":
        if len(args) != 2:
            print("usage: parse_input.py --clear <path/to/input-file>", file=sys.stderr)
            sys.exit(2)
        path = args[1]
        if not os.path.isfile(path):
            print(f"not a file: {path}", file=sys.stderr)
            sys.exit(2)
        data = json.load(sys.stdin)
        removed = clear(path, data.get("remove_lines", []))
        print(json.dumps({"removed": removed}))
        return

    if len(args) != 1:
        print("usage: parse_input.py <path/to/input-file>", file=sys.stderr)
        sys.exit(2)

    path = args[0]
    if not os.path.isfile(path):
        print(f"not a file: {path}", file=sys.stderr)
        sys.exit(2)

    entries, errors = parse(path)

    if errors:
        for error in errors:
            print(error, file=sys.stderr)
        sys.exit(1)

    print(json.dumps({"entries": entries}, ensure_ascii=False, indent=2))


if __name__ == "__main__":
    main()

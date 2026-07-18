#!/usr/bin/env python3
"""Validate and parse an add-english-word-to-anki input file.

Each non-empty line must contain exactly one word/phrase marked with
underscores, e.g.: Just _pin_ a medal to me body.

On success, prints a JSON array of {"line": N, "word": ..., "sentence": ...}
objects to stdout (sentence has the underscores stripped) and exits 0.
On failure, prints one error per invalid line to stderr and exits 1.
"""
import json
import re
import sys

MARKER_RE = re.compile(r"_(.+?)_")


def parse(path):
    entries = []
    errors = []
    with open(path, encoding="utf-8") as f:
        for line_no, raw_line in enumerate(f, start=1):
            line = raw_line.strip()
            if not line:
                continue
            matches = MARKER_RE.findall(line)
            if len(matches) == 0:
                errors.append(f"line {line_no}: no word marked with _..._: {line}")
                continue
            if len(matches) > 1:
                errors.append(
                    f"line {line_no}: multiple words marked with _..._ ({', '.join(matches)}): {line}"
                )
                continue
            word = matches[0]
            sentence = MARKER_RE.sub(lambda m: m.group(1), line, count=1)
            entries.append({"line": line_no, "word": word, "sentence": sentence})
    return entries, errors


def main():
    if len(sys.argv) != 2:
        print("usage: parse_input.py <path/to/input-file.md>", file=sys.stderr)
        sys.exit(2)

    entries, errors = parse(sys.argv[1])

    if errors:
        for error in errors:
            print(error, file=sys.stderr)
        sys.exit(1)

    print(json.dumps(entries, ensure_ascii=False, indent=2))


if __name__ == "__main__":
    main()

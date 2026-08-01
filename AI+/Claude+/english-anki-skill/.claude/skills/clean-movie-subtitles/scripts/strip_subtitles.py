#!/usr/bin/env python3
"""Mechanically strip a movie-subtitle file down to plain dialogue text.

Usage: strip_subtitles.py "<input path>"

Reads an `.srt` or plain-text subtitle file and removes the deterministic, non-linguistic noise so a
caller can then do the judgment work (sentence-per-line reflow, punctuation, profanity restoration,
speaker-label removal). Specifically it drops:

- SRT `-->` timestamp lines and their bare-integer cue-index lines.
- Standalone timestamp lines and leading timestamp prefixes in plain text
  (`[00:01:23]`, `00:01:23`, `00:01:23,456` ...), conservatively so dialogue that merely mentions a
  short `M:SS` time is preserved.
- Inline formatting tags: HTML-ish `<i>`/`<b>`/`<font ...>` and `{...}` override blocks.
- Whitespace-only / empty lines.

It deliberately **keeps** sound/scene cues (`[music]`, `(GUNSHOT)`) and speaker labels (`JOHN:`)
verbatim — removing speaker labels is a judgment call left to the caller.

Prints a JSON object to stdout on success (exit 0):
    {"output_path", "format", "text", "input_lines", "output_lines"}
`output_path` is the input path with a ` clean` suffix inserted before the extension
(`/tmp/Backrooms 2026.txt` -> `/tmp/Backrooms 2026 clean.txt`); `format` is "srt" or "plain". The
script does **not** write any file — the caller writes the cleaned result to `output_path`.

On an unreadable/missing input path it prints a JSON `{"error": ...}` to stderr and exits 2.
"""
import argparse
import json
import re
import sys
from pathlib import Path

# SRT cue line, e.g. "00:01:23,456 --> 00:01:25,789" (comma or dot for the millis; extra coords ok).
TIMESTAMP_ARROW_RE = re.compile(
    r"^\s*\d{1,2}:\d{2}:\d{2}[,.]\d{3}\s*-->\s*\d{1,2}:\d{2}:\d{2}[,.]\d{3}.*$"
)
# A bare integer line — an SRT cue index when it directly precedes a timestamp cue line.
INDEX_RE = re.compile(r"^\s*\d+\s*$")
# A line that is *only* a timestamp token (optionally bracketed), e.g. "[00:01:23]", "12:30".
STANDALONE_TS_RE = re.compile(
    r"^\s*[\[(]?\s*\d{1,2}:\d{2}(?::\d{2})?(?:[.,]\d{1,3})?\s*[\])]?\s*$"
)
# A leading timestamp prefix to strip: bracketed any-length, or a bare full HH:MM:SS (two colons).
# A bare M:SS prefix (one colon) is left alone so dialogue like "1:23 was the score" survives.
LEADING_TS_RE = re.compile(
    r"^\s*(?:[\[(]\s*\d{1,2}:\d{2}(?::\d{2})?(?:[.,]\d{1,3})?\s*[\])]"
    r"|\d{1,2}:\d{2}:\d{2}(?:[.,]\d{1,3})?)\s+"
)
# Inline formatting: HTML-ish tags and {...} override blocks (e.g. SSA/ASS remnants).
TAG_RE = re.compile(r"<[^>]+>")
BRACE_RE = re.compile(r"\{[^}]*\}")


def detect_format(text):
    """"srt" if the text contains any `-->` timestamp cue line, else "plain"."""
    for line in text.splitlines():
        if TIMESTAMP_ARROW_RE.match(line):
            return "srt"
    return "plain"


def strip(text):
    """Remove timestamps, cue indices, formatting tags and empty lines; keep cues/speaker labels."""
    raw_lines = text.splitlines()
    n = len(raw_lines)
    out = []
    for i, line in enumerate(raw_lines):
        # SRT cue index: a bare integer directly before a `-->` timestamp cue line.
        if INDEX_RE.match(line) and i + 1 < n and TIMESTAMP_ARROW_RE.match(raw_lines[i + 1]):
            continue
        # SRT `-->` timestamp line.
        if TIMESTAMP_ARROW_RE.match(line):
            continue
        # Drop inline formatting first, so a wrapped/tagged timestamp is still recognised below.
        cleaned = BRACE_RE.sub("", TAG_RE.sub("", line))
        # A line that is only a timestamp token.
        if STANDALONE_TS_RE.match(cleaned):
            continue
        # A leading timestamp prefix on an otherwise-real line.
        cleaned = LEADING_TS_RE.sub("", cleaned)
        # Whitespace-only / empty line.
        if not cleaned.strip():
            continue
        out.append(cleaned.rstrip())
    return "\n".join(out)


def derive_output_path(input_path):
    """Insert a ` clean` suffix before the extension, preserving directory and spaces.

    "Backrooms 2026.txt" -> "Backrooms 2026 clean.txt"; a name with no extension -> append " clean".
    """
    p = Path(input_path)
    if p.suffix:
        new_name = f"{p.stem} clean{p.suffix}"
    else:
        new_name = f"{p.name} clean"
    return str(p.with_name(new_name))


def process(input_path):
    text = Path(input_path).read_text(encoding="utf-8", errors="replace")
    cleaned = strip(text)
    return {
        "output_path": derive_output_path(input_path),
        "format": detect_format(text),
        "text": cleaned,
        "input_lines": len(text.splitlines()),
        "output_lines": len(cleaned.splitlines()),
    }


def parse_args(argv):
    parser = argparse.ArgumentParser(description="Strip a subtitle file to plain dialogue text.")
    parser.add_argument("input", help="path to the .srt / plain-text subtitle file")
    return parser.parse_args(argv)


def main(argv=None):
    args = parse_args(argv)
    try:
        result = process(args.input)
    except OSError as exc:
        print(json.dumps({"error": f"cannot read {args.input!r}: {exc}"}), file=sys.stderr)
        sys.exit(2)
    print(json.dumps(result))


if __name__ == "__main__":
    main()

#!/usr/bin/env python3
"""Build a deterministic media-filename slug for a note's Picture/audio files.

Usage: slugify.py "<english-or-word>" <pos>
           [--field english|definition|synonym1|antonym1] [--ext jpg|mp3]
       slugify.py "<english-or-word>" <pos> --all-media

- The first argument is the word text (typically the note's `English` value, so it may carry an
  `a`/`an`/`to` prefix, which is stripped) or any surface word.
- `<pos>` is the plain part-of-speech word (noun/verb/adjective/...).
- `--field` appends a field suffix (used for the four audio fields); omit it for the Picture slug.
- `--ext` appends a file extension (e.g. `jpg` for Picture, `mp3` for audio); omit for the bare slug.

The slug is `word-pos[-field]`: lowercased, a leading `a `/`an `/`to ` dropped, and each run of
non-alphanumeric characters collapsed to a single `-` (edge hyphens stripped). Being deterministic,
re-processing the same note overwrites its media file via `storeMediaFile` rather than accumulating.

Examples:
    slugify.py "a beggar" noun --field english --ext mp3   -> beggar-noun-english.mp3
    slugify.py "to bat around" verb --ext jpg               -> bat-around-verb.jpg
    slugify.py "reluctant" adjective                        -> reluctant-adjective

Prints a JSON object to stdout: {"slug": ..., "filename": ...} (`filename` == `slug` when no --ext).

`--all-media` (exclusive with --field/--ext) prints every media filename a note can need in one
call — the Picture jpg plus the four audio mp3s, keyed by field suffix:
    slugify.py "a beggar" noun --all-media
    -> {"picture": "beggar-noun.jpg", "english": "beggar-noun-english.mp3",
        "definition": "beggar-noun-definition.mp3", "synonym1": "beggar-noun-synonym1.mp3",
        "antonym1": "beggar-noun-antonym1.mp3"}
"""
import argparse
import json
import re
import sys

ARTICLE_PREFIXES = ("a ", "an ", "to ")


def slugify_word(text):
    """Lowercase, drop a leading a/an/to, and collapse non-alphanumerics to single hyphens."""
    low = text.strip().lower()
    for prefix in ARTICLE_PREFIXES:
        if low.startswith(prefix):
            low = low[len(prefix):]
            break
    return re.sub(r"[^a-z0-9]+", "-", low).strip("-")


AUDIO_FIELDS = ("english", "definition", "synonym1", "antonym1")


def build_slug(word, pos, field=None, ext=None):
    parts = [slugify_word(word), slugify_word(pos)]
    if field:
        parts.append(slugify_word(field))
    slug = "-".join(p for p in parts if p)
    filename = f"{slug}.{ext}" if ext else slug
    return {"slug": slug, "filename": filename}


def build_all_media(word, pos):
    """Every media filename a note can need: the Picture jpg + the four audio mp3s."""
    filenames = {"picture": build_slug(word, pos, ext="jpg")["filename"]}
    for field in AUDIO_FIELDS:
        filenames[field] = build_slug(word, pos, field=field, ext="mp3")["filename"]
    return filenames


def parse_args(argv):
    parser = argparse.ArgumentParser(description="Build a deterministic media-filename slug.")
    parser.add_argument("word")
    parser.add_argument("pos")
    parser.add_argument("--field", default=None)
    parser.add_argument("--ext", default=None)
    parser.add_argument("--all-media", action="store_true")
    return parser.parse_args(argv)


def main(argv=None):
    args = parse_args(argv)
    if args.all_media and (args.field or args.ext):
        print("error: --all-media cannot be combined with --field/--ext", file=sys.stderr)
        sys.exit(2)
    if not build_slug(args.word, args.pos)["slug"]:
        print("error: empty slug (word and pos produced no alphanumeric characters)", file=sys.stderr)
        sys.exit(1)
    if args.all_media:
        print(json.dumps(build_all_media(args.word, args.pos)))
        return
    print(json.dumps(build_slug(args.word, args.pos, args.field, args.ext)))


if __name__ == "__main__":
    main()

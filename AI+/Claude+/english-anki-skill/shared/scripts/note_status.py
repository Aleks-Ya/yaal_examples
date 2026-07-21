#!/usr/bin/env python3
"""Report the backfill worklist and completeness of an English Anki note from its fields + tags.

This is the single source of truth for the *judgment-free* bookkeeping both skills need: which
Claude-owned fields are still empty (respecting `~api::absent::*` tags), which audio fields need
synthesizing, whether the note is fully complete, and whether the `en::to-refine` tag should be
dropped. All linguistic work (POS, definitions, translations, image relevance) stays with the model;
this script only inspects presence/absence of values.

Reads a JSON object from stdin (the `notesInfo` shape):
    {
      "fields": {"English": "a beggar", "Definition": "...", "Picture": "", ...},
      "tags": ["en::parts::noun::countable", "~api::absent::synonyms", "en::to-refine"]
    }
Field values may be raw Anki HTML. A field counts as non-empty if it has visible text, an `<img>`
(Picture), or a `[sound:...]` reference (audio) — a bare `<br>`/`&nbsp;`/whitespace counts as empty.

Prints a JSON object to stdout:
    {
      "empty_claude_fields":  [...],  // backfillable text fields empty AND not absence-tagged
      "absent_ok_fields":     [...],  // empty fields legitimately covered by an absence tag (skip)
      "skipped_fields":       [...],  // empty fields excluded by --no-pictures (not backfill work)
      "audio_to_generate":    [...],  // audio fields whose source text is non-empty but audio empty
      "complete":             bool,   // every Claude field non-empty or absence-tagged + audio present
      "remove_refine_tag":    bool,   // complete AND the note currently carries en::to-refine
      "incomplete_reasons":   [...]   // human-readable notes on what's still missing (for the report)
    }

With the `--no-pictures` CLI flag, an empty un-absence-tagged Picture is reported in
`skipped_fields` instead of `empty_claude_fields` and does not block `complete` /
`remove_refine_tag`: the skills' no-pictures mode deliberately leaves it unfilled and untagged,
and that must not keep an otherwise finished note in the `en::to-refine` queue.

Usage: call once up front to get `empty_claude_fields` + `audio_to_generate` (the worklist), then
after writing the backfilled values re-run it on the updated note state to get the final
`audio_to_generate`, `complete`, and `remove_refine_tag`. It is a pure function of the passed-in
state, so the caller may instead pass the *intended* post-backfill field values to get the final
verdict in a single call. `English` emptiness (a broken note) is the caller's precondition to skip a
note and is not represented here.
"""
import json
import re
import sys

# Claude-owned text fields that the backfill routine fills (the `Created by = Claude` rows of
# field-plan.md, excluding `English` -- the note's identity, set/normalized separately -- and the
# audio fields handled via AUDIO_SOURCE_MAP below).
CLAUDE_TEXT_FIELDS = [
    "Transcription",
    "Definition",
    "Picture",
    "Russian",
    "Synonym1",
    "Synonyms",
    "Antonym1",
    "Antonyms",
    "Examples1-generated",
]

# Fields that may legitimately stay empty when the value truly does not exist, marked by an
# `~api::absent::*` tag rather than backfilled.
ABSENCE_TAGS = {
    "Synonym1": "~api::absent::synonym1",
    "Synonyms": "~api::absent::synonyms",
    "Antonym1": "~api::absent::antonym1",
    "Antonyms": "~api::absent::antonyms",
    "Picture": "~api::absent::picture",
}
ABSENCE_ELIGIBLE = set(ABSENCE_TAGS)

# audio field -> the text field it is synthesized from. An audio field is expected only when its
# source text field is non-empty; the obsolete Synonyms-/Antonyms-audio-generated have no source.
AUDIO_SOURCE_MAP = {
    "English-audio-generated": "English",
    "Definition-audio-generated": "Definition",
    "Synonym1-audio-generated": "Synonym1",
    "Antonym1-audio-generated": "Antonym1",
}

REFINE_TAG = "en::to-refine"

TAG_RE = re.compile(r"<[^>]+>")


def is_empty(value):
    """True unless the field carries visible text, an <img> (Picture), or a [sound:] (audio)."""
    if not value:
        return True
    if re.search(r"<img\b", value, re.IGNORECASE) or "[sound:" in value:
        return False
    text = TAG_RE.sub(" ", value).replace("&nbsp;", " ")
    return not text.strip()


def compute_status(fields, tags, no_pictures=False):
    tags = set(tags)

    def empty(name):
        return is_empty(fields.get(name, ""))

    empty_claude_fields = []
    absent_ok_fields = []
    skipped_fields = []
    incomplete_reasons = []

    for name in CLAUDE_TEXT_FIELDS:
        if not empty(name):
            continue
        if name in ABSENCE_ELIGIBLE and ABSENCE_TAGS[name] in tags:
            absent_ok_fields.append(name)
        elif no_pictures and name == "Picture":
            skipped_fields.append(name)
        else:
            empty_claude_fields.append(name)
            incomplete_reasons.append(f"missing {name}")

    audio_to_generate = []
    for audio_field, source in AUDIO_SOURCE_MAP.items():
        if empty(source):
            continue  # no audio expected when there is nothing to speak
        if empty(audio_field):
            audio_to_generate.append(audio_field)
            incomplete_reasons.append(f"missing {audio_field}")

    complete = not empty_claude_fields and not audio_to_generate
    remove_refine_tag = complete and REFINE_TAG in tags

    return {
        "empty_claude_fields": empty_claude_fields,
        "absent_ok_fields": absent_ok_fields,
        "skipped_fields": skipped_fields,
        "audio_to_generate": audio_to_generate,
        "complete": complete,
        "remove_refine_tag": remove_refine_tag,
        "incomplete_reasons": incomplete_reasons,
    }


def main():
    args = sys.argv[1:]
    no_pictures = "--no-pictures" in args
    unknown = [a for a in args if a != "--no-pictures"]
    if unknown:
        print(f"unknown argument(s): {' '.join(unknown)}", file=sys.stderr)
        sys.exit(2)
    data = json.load(sys.stdin)
    result = compute_status(data.get("fields", {}), data.get("tags", []), no_pictures=no_pictures)
    print(json.dumps(result, ensure_ascii=False))


if __name__ == "__main__":
    main()

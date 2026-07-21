#!/usr/bin/env python3
"""Decide which existing Anki note(s), if any, are a genuine duplicate of a word being added.

Direct mode (preferred): find_duplicate.py "<word>" "<pos_tag>"
    Queries AnkiConnect itself (default http://localhost:8765, override via the ANKICONNECT_URL
    env var — handy for tests): one `findNotes` with a wildcard on the normalized word scoped to
    the `En-word-or-sentence` note type, one `notesInfo` for the hits. The caller never has to
    pull the candidates' full field payloads into its own context.

    Prints a JSON object to stdout:
        {
          "duplicates": [id, ...],       // empty if none; >1 means ambiguous, worth flagging
          "candidates_checked": N,
          "note": { ... } | null         // only when exactly one duplicate: a trimmed payload
        }
    `note` carries everything the skills need to update the duplicate without a follow-up
    notesInfo: {"id", "tags", "fields": {only the small text fields, incl. English and
    Example-real-life}, "status": <note_status.compute_status output on the FULL field set>}.
    On an AnkiConnect/network error it prints the error to stderr and exits 1.

Stdin mode (candidates supplied by the caller) reads a JSON object:
    {
      "word": "beggar",              // the base form used for the English field (see SKILL.md
                                      // step 2.1.4) -- WITH or WITHOUT its a/an/to prefix, either
                                      // is fine since prefixes are normalized away below
      "pos_tag": "en::parts::noun::countable",
      "candidates": [
        {"id": 1579307261208, "english": "beggar", "tags": ["en::parts::noun", "..."]},
        ...
      ]
    }
and prints {"duplicates": [id, ...]}.

A candidate counts as a duplicate only if BOTH hold:
- its English field matches `word` after stripping a leading "a "/"an "/"to " (case-insensitive)
  from both sides and lowercasing;
- it has some `en::parts::*` tag that is equal to, an ancestor of, or a descendant of `pos_tag`
  (via `::`-segment prefix, e.g. `en::parts::noun` matches `en::parts::noun::countable`).
"""
import json
import os
import sys
import urllib.request

import note_status

ARTICLE_PREFIXES = ("a ", "an ", "to ")

DEFAULT_ANKI_URL = "http://localhost:8765"
NOTE_TYPE = "En-word-or-sentence"

# The small text fields returned in the trimmed `note` payload — everything the skills read from a
# duplicate (English for article normalization, Example-real-life for the sentence append, the rest
# as context for backfilling). The big generated-HTML and media fields stay out; their emptiness is
# already covered by `status`.
TRIMMED_FIELDS = (
    "English",
    "Transcription",
    "Definition",
    "Russian",
    "Synonym1",
    "Synonyms",
    "Antonym1",
    "Antonyms",
    "Example-real-life",
)


def normalize_word(text):
    low = text.strip().lower()
    for prefix in ARTICLE_PREFIXES:
        if low.startswith(prefix):
            return low[len(prefix):]
    return low


def pos_family_match(pos_tag, tags):
    for tag in tags:
        if not tag.startswith("en::parts::"):
            continue
        if tag == pos_tag or tag.startswith(pos_tag + "::") or pos_tag.startswith(tag + "::"):
            return True
    return False


def find_duplicates(word, pos_tag, candidates):
    target_word = normalize_word(word)
    duplicates = []
    for candidate in candidates:
        if normalize_word(candidate["english"]) != target_word:
            continue
        if not pos_family_match(pos_tag, candidate.get("tags", [])):
            continue
        duplicates.append(candidate["id"])
    return duplicates


def anki_request(action, **params):
    url = os.environ.get("ANKICONNECT_URL", DEFAULT_ANKI_URL)
    body = json.dumps({"action": action, "version": 6, "params": params}).encode("utf-8")
    request = urllib.request.Request(
        url, data=body, headers={"Content-Type": "application/json"}, method="POST"
    )
    with urllib.request.urlopen(request) as response:
        payload = json.load(response)
    if payload.get("error"):
        raise ValueError(f"AnkiConnect {action}: {payload['error']}")
    return payload.get("result")


def check_word(word, pos_tag):
    """Run the whole duplicate check against AnkiConnect for one word."""
    query = f'note:{NOTE_TYPE} "English:*{normalize_word(word)}*"'
    note_ids = anki_request("findNotes", query=query)
    notes = anki_request("notesInfo", notes=note_ids) if note_ids else []

    candidates = [
        {
            "id": note["noteId"],
            "english": note["fields"].get("English", {}).get("value", ""),
            "tags": note.get("tags", []),
        }
        for note in notes
    ]
    duplicates = find_duplicates(word, pos_tag, candidates)

    result = {"duplicates": duplicates, "candidates_checked": len(candidates), "note": None}
    if len(duplicates) == 1:
        note = next(n for n in notes if n["noteId"] == duplicates[0])
        fields = {name: value.get("value", "") for name, value in note["fields"].items()}
        result["note"] = {
            "id": note["noteId"],
            "tags": note.get("tags", []),
            "fields": {name: fields.get(name, "") for name in TRIMMED_FIELDS},
            "status": note_status.compute_status(fields, note.get("tags", [])),
        }
    return result


def main():
    if len(sys.argv) == 3:
        try:
            result = check_word(sys.argv[1], sys.argv[2])
        except Exception as e:
            print(f"error: {e}", file=sys.stderr)
            sys.exit(1)
        print(json.dumps(result, ensure_ascii=False))
        return

    if len(sys.argv) != 1:
        print(
            'usage: find_duplicate.py "<word>" "<pos_tag>"   (queries AnkiConnect directly)\n'
            "       find_duplicate.py   (JSON {word, pos_tag, candidates} on stdin)",
            file=sys.stderr,
        )
        sys.exit(2)

    data = json.load(sys.stdin)
    duplicates = find_duplicates(data["word"], data["pos_tag"], data["candidates"])
    print(json.dumps({"duplicates": duplicates}))


if __name__ == "__main__":
    main()

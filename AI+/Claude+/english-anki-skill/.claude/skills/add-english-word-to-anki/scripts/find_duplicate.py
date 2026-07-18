#!/usr/bin/env python3
"""Decide which candidate Anki note(s), if any, are a genuine duplicate of a word being added.

Reads a JSON object from stdin:
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

A candidate counts as a duplicate only if BOTH hold:
- its English field matches `word` after stripping a leading "a "/"an "/"to " (case-insensitive)
  from both sides and lowercasing;
- it has some `en::parts::*` tag that is equal to, an ancestor of, or a descendant of `pos_tag`
  (via `::`-segment prefix, e.g. `en::parts::noun` matches `en::parts::noun::countable`).

Prints a JSON object to stdout: {"duplicates": [id, ...]} (empty if none match; more than one id
means multiple existing notes matched, which is unusual and worth flagging rather than picking
one arbitrarily).
"""
import json
import sys

ARTICLE_PREFIXES = ("a ", "an ", "to ")


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


def main():
    data = json.load(sys.stdin)
    duplicates = find_duplicates(data["word"], data["pos_tag"], data["candidates"])
    print(json.dumps({"duplicates": duplicates}))


if __name__ == "__main__":
    main()

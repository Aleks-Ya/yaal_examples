#!/usr/bin/env python3
"""Fetch the current source::* tag vocabulary from Anki, fresh on every run.

No-arg CLI (the only mode): list_source_tags.py
    Calls AnkiConnect `getTags` (default http://localhost:8765, override via the ANKICONNECT_URL
    env var — handy for tests), keeps only the tags under the `source::` prefix, sorts them, and
    prints a JSON object to stdout:
        {"source_tags": ["source::blog::...", "source::company", ...]}
    On an AnkiConnect/network error it prints the error to stderr and exits 1.

This replaces the old static shared/assets/word-sources-anki-tags.md snapshot so a source tag added
in Anki after the snapshot (e.g. source::movie::backrooms) is picked up without editing any file.
"""
import json
import os
import sys
import urllib.request

DEFAULT_ANKI_URL = "http://localhost:8765"
SOURCE_PREFIX = "source::"


def filter_source_tags(tags):
    """Keep only tags under the `source::` prefix, sorted.

    The prefix (not a bare substring) match keeps out unrelated tags that merely contain a
    `source::` segment deeper in their hierarchy, e.g.
    `it::big-data::spark::streaming::structured::source::kafka`.
    """
    return sorted(tag for tag in tags if tag.startswith(SOURCE_PREFIX))


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


def fetch_source_tags():
    """Return the live source::* vocabulary from Anki."""
    return filter_source_tags(anki_request("getTags"))


def main():
    if len(sys.argv) != 1:
        print("usage: list_source_tags.py   (queries AnkiConnect directly)", file=sys.stderr)
        sys.exit(2)

    try:
        tags = fetch_source_tags()
    except Exception as e:
        print(f"error: {e}", file=sys.stderr)
        sys.exit(1)
    print(json.dumps({"source_tags": tags}, ensure_ascii=False))


if __name__ == "__main__":
    main()

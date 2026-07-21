#!/usr/bin/env python3
"""Search the keyless Openverse image API for candidate pictures to illustrate a word.

Usage: search_images.py <query> [--limit N]

- `query` is a natural-language image query (e.g. "beggar person asking for money on street").
- `--limit` (default 8) caps how many candidates are returned.

Returns a JSON array on stdout, one object per candidate:
    {"url": ..., "thumbnail": ..., "title": ..., "tags": [...], "source": ..., "license": ...}
`url` is a direct image URL suitable for fetch_and_resize_image.py; `title`/`tags` describe the
image so the caller can rank/verify relevance. Zero results prints `[]` (exit 0).

The endpoint is `https://api.openverse.org/v1/images/` (override the host via the
`OPENVERSE_API_BASE` env var, handy for tests). No API key required. Mature content is excluded
(Openverse default). On request/decode failure, prints an error to stderr and exits 1.
"""
import argparse
import json
import os
import sys
import urllib.error
import urllib.parse
import urllib.request

DEFAULT_LIMIT = 8
DEFAULT_API_BASE = "https://api.openverse.org"

# Openverse rejects requests without a browser-ish User-Agent from some networks.
USER_AGENT = (
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) "
    "Chrome/124.0.0.0 Safari/537.36"
)


def parse_results(payload, limit):
    """Turn an Openverse image-search response into a trimmed list of candidate dicts.

    Flattens each result's tag objects to their bare `name`. Returns at most `limit` candidates;
    a payload with no results yields an empty list.
    """
    candidates = []
    for result in (payload.get("results") or [])[:limit]:
        tags = [t.get("name") for t in (result.get("tags") or []) if t.get("name")]
        candidates.append(
            {
                "url": result.get("url"),
                "thumbnail": result.get("thumbnail"),
                "title": result.get("title"),
                "tags": tags,
                "source": result.get("source"),
                "license": result.get("license"),
            }
        )
    return candidates


def search(query, limit):
    base = os.environ.get("OPENVERSE_API_BASE", DEFAULT_API_BASE).rstrip("/")
    params = urllib.parse.urlencode({"q": query, "page_size": limit})
    url = f"{base}/v1/images/?{params}"
    request = urllib.request.Request(url, headers={"User-Agent": USER_AGENT})
    try:
        with urllib.request.urlopen(request) as response:
            payload = json.load(response)
    except urllib.error.HTTPError as e:
        body = e.read().decode("utf-8", "replace")
        try:
            detail = json.loads(body).get("detail")
        except (json.JSONDecodeError, AttributeError):
            detail = None
        raise ValueError(f"HTTP {e.code}: {detail or body.strip() or e.reason}") from None
    return parse_results(payload, limit)


def parse_args(argv):
    parser = argparse.ArgumentParser(description="Search Openverse for candidate images.")
    parser.add_argument("query")
    parser.add_argument("--limit", type=int, default=DEFAULT_LIMIT)
    return parser.parse_args(argv)


def main(argv=None):
    args = parse_args(argv)

    try:
        candidates = search(args.query, args.limit)
    except Exception as e:
        print(f"error: {e}", file=sys.stderr)
        sys.exit(1)

    print(json.dumps(candidates))


if __name__ == "__main__":
    main()

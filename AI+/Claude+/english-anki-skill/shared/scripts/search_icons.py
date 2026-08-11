#!/usr/bin/env python3
"""Search the keyless Iconify API for an icon of a word, and fetch it as a card-ready SVG.

Two modes:

1. Search — `search_icons.py <query> [--limit N]`
   Returns a JSON array on stdout, one object per candidate:
       {"id": "mdi:ladder", "prefix": "mdi", "name": "ladder", "collection": "Material Design Icons"}
   Only candidates whose icon *name* matches the query are kept (the icon name equals the query, or
   is a style variant of it such as `ladder-fill`/`ladder-outline`) — Iconify's search is fuzzy, and
   a name match is what makes a keyless pick safe without looking at the image. Exact names rank
   before variants, and tidy monotone collections before the rest. No match prints `[]` (exit 0).
   `--limit` (default 8) caps the returned candidates.

2. Fetch — `search_icons.py --fetch <prefix:name> <output_path.svg>`
   Downloads the icon recoloured to slate and wraps it in the canonical flashcard SVG: a 512x512
   canvas with a white rounded card behind the icon, so it stays legible in Anki's night mode and on
   AnkiDroid (a transparent black-stroke icon would nearly vanish there). The root is
   `width/height=256`, so the stored `<img src="...">` needs no sizing attribute. Prints
   {"path": ..., "id": ...}; on failure prints an error to stderr and exits 1.

The endpoint is `https://api.iconify.design` (override the host via the `ICONIFY_API_BASE` env var,
handy for tests). No API key required.
"""
import argparse
import json
import os
import re
import sys
import urllib.error
import urllib.parse
import urllib.request

DEFAULT_LIMIT = 8
DEFAULT_API_BASE = "https://api.iconify.design"

# Iconify's CDN answers 403 to urllib's default User-Agent.
USER_AGENT = (
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) "
    "Chrome/124.0.0.0 Safari/537.36"
)

# Iconify's search endpoint rejects a limit below 32, so always ask for a roomy page and trim
# locally after the name filter has thrown the fuzzy matches away.
SEARCH_PAGE_SIZE = 64

# Icon colour and the card behind it (see the module docstring).
ICON_COLOR = "#334155"
CARD_FILL = "#ffffff"
CARD_STROKE = "#e2e8f0"
CANVAS = 512
DISPLAY_SIZE = 256
CARD_RADIUS = 72
ICON_INSET = 64  # leaves a 384x384 box for the icon itself

# Monotone, visually consistent collections first; anything else keeps its search order behind them.
PREFERRED_PREFIXES = (
    "mdi",
    "material-symbols",
    "tabler",
    "lucide",
    "ph",
    "bi",
    "carbon",
    "solar",
    "fluent",
)

# Style suffixes Iconify collections append to the *same* concept (`ladder-fill`, `ladder-outline`,
# `ladder-24-regular`). Only these may follow the query word — a candidate like `ladder-truck` is a
# different object and must not pass the name filter.
STYLE_SUFFIXES = frozenset(
    """
    fill filled line lined outline outlined bold light thin regular medium
    solid duotone twotone two tone mono monochrome color colour flat
    sharp rounded round square circle alt remix o s r
    """.split()
)

SVG_ROOT_RE = re.compile(r"<svg\b([^>]*)>(.*)</svg>\s*$", re.IGNORECASE | re.DOTALL)
VIEWBOX_RE = re.compile(r'viewBox\s*=\s*"([^"]+)"', re.IGNORECASE)


def normalize_query(query):
    """Lowercase the query and collapse non-alphanumerics to single hyphens (icon-name shape)."""
    return re.sub(r"[^a-z0-9]+", "-", query.strip().lower()).strip("-")


def name_matches(icon_name, query_slug):
    """True when the icon name is the query itself or a style variant of it (`ladder-fill`).

    Anything else is rejected — a name that merely contains the query (`step-ladder`) or extends it
    into another concept (`ladder-truck`). Iconify's search is fuzzy, and only this narrow match
    makes picking an icon safe without looking at it.
    """
    if not query_slug:
        return False
    if icon_name == query_slug:
        return True
    if not icon_name.startswith(f"{query_slug}-"):
        return False
    rest = icon_name[len(query_slug) + 1:].split("-")
    return all(part.isdigit() or part in STYLE_SUFFIXES for part in rest)


def rank_key(candidate, query_slug, order):
    """Sort exact names before variants, preferred collections before the rest, else search order."""
    prefix = candidate["prefix"]
    try:
        prefix_rank = PREFERRED_PREFIXES.index(prefix)
    except ValueError:
        prefix_rank = len(PREFERRED_PREFIXES)
    return (0 if candidate["name"] == query_slug else 1, prefix_rank, order)


def parse_results(payload, query, limit):
    """Turn an Iconify search response into a ranked list of name-matching candidate dicts."""
    query_slug = normalize_query(query)
    collections = payload.get("collections") or {}
    candidates = []
    for order, icon_id in enumerate(payload.get("icons") or []):
        prefix, _, name = icon_id.partition(":")
        if not name or not name_matches(name, query_slug):
            continue
        candidates.append(
            {
                "id": icon_id,
                "prefix": prefix,
                "name": name,
                "collection": (collections.get(prefix) or {}).get("name"),
                "_order": order,
            }
        )
    candidates.sort(key=lambda c: rank_key(c, query_slug, c["_order"]))
    for candidate in candidates:
        del candidate["_order"]
    return candidates[:limit]


def wrap_icon_svg(icon_svg, icon_id):
    """Wrap a raw Iconify SVG in the canonical white-card canvas, preserving its aspect ratio.

    The icon is nested as an inner <svg> with its own viewBox, so the browser centres and scales it
    into the 384x384 content box without any arithmetic here.
    """
    if "<script" in icon_svg.lower():
        raise ValueError(f"{icon_id}: icon SVG contains a script element")
    match = SVG_ROOT_RE.search(icon_svg.strip())
    if not match:
        raise ValueError(f"{icon_id}: response is not an SVG document")
    attributes, inner = match.group(1), match.group(2)
    viewbox_match = VIEWBOX_RE.search(attributes)
    if not viewbox_match:
        raise ValueError(f"{icon_id}: icon SVG has no viewBox")
    viewbox = viewbox_match.group(1)
    content_size = CANVAS - 2 * ICON_INSET
    return (
        f'<svg xmlns="http://www.w3.org/2000/svg" width="{DISPLAY_SIZE}" height="{DISPLAY_SIZE}"'
        f' viewBox="0 0 {CANVAS} {CANVAS}">'
        f'<rect width="{CANVAS}" height="{CANVAS}" rx="{CARD_RADIUS}"'
        f' fill="{CARD_FILL}" stroke="{CARD_STROKE}" stroke-width="4"/>'
        f'<svg x="{ICON_INSET}" y="{ICON_INSET}" width="{content_size}" height="{content_size}"'
        f' viewBox="{viewbox}" preserveAspectRatio="xMidYMid meet">{inner}</svg>'
        "</svg>"
    )


def _api_base():
    return os.environ.get("ICONIFY_API_BASE", DEFAULT_API_BASE).rstrip("/")


def _get(url):
    request = urllib.request.Request(url, headers={"User-Agent": USER_AGENT})
    try:
        with urllib.request.urlopen(request) as response:
            return response.read().decode("utf-8")
    except urllib.error.HTTPError as e:
        raise ValueError(f"HTTP {e.code}: {e.reason}") from None


def search(query, limit):
    params = urllib.parse.urlencode({"query": query, "limit": SEARCH_PAGE_SIZE})
    payload = json.loads(_get(f"{_api_base()}/search?{params}"))
    return parse_results(payload, query, limit)


def fetch(icon_id, output_path):
    prefix, _, name = icon_id.partition(":")
    if not prefix or not name:
        raise ValueError(f"{icon_id}: expected a <prefix>:<name> icon id")
    params = urllib.parse.urlencode({"color": ICON_COLOR})
    icon_svg = _get(f"{_api_base()}/{prefix}/{name}.svg?{params}")
    card = wrap_icon_svg(icon_svg, icon_id)
    with open(output_path, "w", encoding="utf-8") as handle:
        handle.write(card)
    return {"path": output_path, "id": icon_id}


def parse_args(argv):
    parser = argparse.ArgumentParser(description="Search Iconify for an icon of a word.")
    parser.add_argument("query", nargs="?")
    parser.add_argument("--limit", type=int, default=DEFAULT_LIMIT)
    parser.add_argument(
        "--fetch",
        nargs=2,
        metavar=("ICON_ID", "OUTPUT_PATH"),
        help="download <prefix:name> as a card-ready SVG at OUTPUT_PATH",
    )
    return parser.parse_args(argv)


def main(argv=None):
    args = parse_args(argv)
    if args.fetch:
        icon_id, output_path = args.fetch
        try:
            print(json.dumps(fetch(icon_id, output_path)))
        except Exception as e:
            print(f"error: {e}", file=sys.stderr)
            sys.exit(1)
        return

    if not args.query:
        print("error: a query is required (or use --fetch)", file=sys.stderr)
        sys.exit(2)
    try:
        candidates = search(args.query, args.limit)
    except Exception as e:
        print(f"error: {e}", file=sys.stderr)
        sys.exit(1)
    print(json.dumps(candidates))


if __name__ == "__main__":
    main()

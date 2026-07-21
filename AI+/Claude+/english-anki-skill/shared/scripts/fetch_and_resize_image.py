#!/usr/bin/env python3
"""Download an image and shrink it to fit within a max dimension before storing in Anki.

Single-URL:  fetch_and_resize_image.py <url> <output_path> [max_dimension]
Batch:       fetch_and_resize_image.py --batch   (JSON array on stdin)

- `url` may be any scheme `urllib.request` supports, including `file://` (handy for tests).
- `output_path`'s extension determines the saved format (e.g. ".jpg" -> JPEG).
- `max_dimension` (default 600) caps the longest side; images already smaller are never
  upscaled.

Single-URL mode prints a JSON object to stdout: {"path": ..., "width": W, "height": H};
on download/decode failure it prints an error to stderr and exits 1.

Batch mode reads a JSON array of {"url": ..., "path": ..., "max_dimension"?: N} from stdin,
downloads the items concurrently, and prints a JSON array of per-item results in input order —
{"path": ..., "width": W, "height": H} on success or {"path": ..., "error": ...} on failure
(a single bad item does not abort the batch). It exits 0 as long as it ran.
"""
import io
import json
import sys
import urllib.request
from concurrent.futures import ThreadPoolExecutor
from pathlib import Path

from PIL import Image, ImageOps

DEFAULT_MAX_DIMENSION = 600
BATCH_MAX_WORKERS = 5

# Many image hosts (e.g. Wikimedia) reject the default urllib user agent with 403.
USER_AGENT = (
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) "
    "Chrome/124.0.0.0 Safari/537.36"
)

EXTENSION_TO_FORMAT = {
    ".jpg": "JPEG",
    ".jpeg": "JPEG",
    ".png": "PNG",
    ".gif": "GIF",
    ".webp": "WEBP",
}


def format_for_path(path):
    suffix = Path(path).suffix.lower()
    try:
        return EXTENSION_TO_FORMAT[suffix]
    except KeyError:
        raise ValueError(f"unsupported output extension: {suffix!r}") from None


def resize_image_bytes(data, max_dimension, output_format):
    image = Image.open(io.BytesIO(data))
    image = ImageOps.exif_transpose(image)
    image.thumbnail((max_dimension, max_dimension), Image.LANCZOS)

    if output_format == "JPEG" and image.mode in ("RGBA", "P", "LA"):
        image = image.convert("RGB")

    buffer = io.BytesIO()
    save_kwargs = {"quality": 85} if output_format == "JPEG" else {}
    image.save(buffer, format=output_format, **save_kwargs)
    return buffer.getvalue()


def fetch_one(url, output_path, max_dimension=DEFAULT_MAX_DIMENSION):
    """Download `url`, shrink to `max_dimension`, save to `output_path`.

    Returns {"path": ..., "width": W, "height": H}. Raises on download/decode/write failure.
    """
    output_format = format_for_path(output_path)
    request = urllib.request.Request(url, headers={"User-Agent": USER_AGENT})
    data = urllib.request.urlopen(request).read()
    resized = resize_image_bytes(data, max_dimension, output_format)

    with open(output_path, "wb") as f:
        f.write(resized)

    with Image.open(io.BytesIO(resized)) as image:
        width, height = image.size
    return {"path": output_path, "width": width, "height": height}


def run_batch(items):
    """Fetch/resize a list of {"url", "path", "max_dimension"?} items concurrently.

    Returns a list of per-item results in input order: the fetch_one dict on success, or
    {"path": ..., "error": ...} on failure. A single bad item does not abort the batch.
    """
    def work(item):
        try:
            return fetch_one(
                item["url"], item["path"], item.get("max_dimension", DEFAULT_MAX_DIMENSION)
            )
        except Exception as e:
            return {"path": item.get("path"), "error": str(e)}

    with ThreadPoolExecutor(max_workers=BATCH_MAX_WORKERS) as executor:
        return list(executor.map(work, items))


def main():
    if len(sys.argv) == 2 and sys.argv[1] == "--batch":
        items = json.load(sys.stdin)
        print(json.dumps(run_batch(items)))
        return

    if len(sys.argv) not in (3, 4):
        print(
            "usage: fetch_and_resize_image.py <url> <output_path> [max_dimension]\n"
            "       fetch_and_resize_image.py --batch   (JSON array on stdin)",
            file=sys.stderr,
        )
        sys.exit(2)

    url = sys.argv[1]
    output_path = sys.argv[2]
    max_dimension = int(sys.argv[3]) if len(sys.argv) == 4 else DEFAULT_MAX_DIMENSION

    try:
        result = fetch_one(url, output_path, max_dimension)
    except Exception as e:
        print(f"error: {e}", file=sys.stderr)
        sys.exit(1)

    print(json.dumps(result))


if __name__ == "__main__":
    main()

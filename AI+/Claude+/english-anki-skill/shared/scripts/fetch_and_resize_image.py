#!/usr/bin/env python3
"""Download an image and shrink it to fit within a max dimension before storing in Anki.

Usage: fetch_and_resize_image.py <url> <output_path> [max_dimension]

- `url` may be any scheme `urllib.request` supports, including `file://` (handy for tests).
- `output_path`'s extension determines the saved format (e.g. ".jpg" -> JPEG).
- `max_dimension` (default 600) caps the longest side; images already smaller are never
  upscaled.

Prints a JSON object to stdout: {"path": ..., "width": W, "height": H}.
On download/decode failure, prints an error to stderr and exits 1.
"""
import io
import json
import sys
import urllib.request
from pathlib import Path

from PIL import Image, ImageOps

DEFAULT_MAX_DIMENSION = 600

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


def main():
    if len(sys.argv) not in (3, 4):
        print("usage: fetch_and_resize_image.py <url> <output_path> [max_dimension]", file=sys.stderr)
        sys.exit(2)

    url = sys.argv[1]
    output_path = sys.argv[2]
    max_dimension = int(sys.argv[3]) if len(sys.argv) == 4 else DEFAULT_MAX_DIMENSION

    try:
        output_format = format_for_path(output_path)
        request = urllib.request.Request(url, headers={"User-Agent": USER_AGENT})
        data = urllib.request.urlopen(request).read()
        resized = resize_image_bytes(data, max_dimension, output_format)
    except Exception as e:
        print(f"error: {e}", file=sys.stderr)
        sys.exit(1)

    with open(output_path, "wb") as f:
        f.write(resized)

    with Image.open(io.BytesIO(resized)) as image:
        width, height = image.size
    print(json.dumps({"path": output_path, "width": width, "height": height}))


if __name__ == "__main__":
    main()

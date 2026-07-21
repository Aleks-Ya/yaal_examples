import io
import json
import subprocess
import sys
from pathlib import Path

from PIL import Image

import fetch_and_resize_image

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "fetch_and_resize_image.py"


def make_image_bytes(size, mode="RGB", color=(200, 100, 50), fmt="PNG"):
    image = Image.new(mode, size, color)
    buffer = io.BytesIO()
    image.save(buffer, format=fmt)
    return buffer.getvalue()


def test_large_image_is_capped_preserving_aspect_ratio():
    data = make_image_bytes((1200, 800))
    resized = fetch_and_resize_image.resize_image_bytes(data, 600, "JPEG")
    with Image.open(io.BytesIO(resized)) as image:
        assert image.size == (600, 400)


def test_small_image_is_not_upscaled():
    data = make_image_bytes((300, 200))
    resized = fetch_and_resize_image.resize_image_bytes(data, 600, "JPEG")
    with Image.open(io.BytesIO(resized)) as image:
        assert image.size == (300, 200)


def test_rgba_source_flattened_to_rgb_for_jpeg_target():
    data = make_image_bytes((100, 100), mode="RGBA", color=(10, 20, 30, 128), fmt="PNG")
    resized = fetch_and_resize_image.resize_image_bytes(data, 600, "JPEG")
    with Image.open(io.BytesIO(resized)) as image:
        assert image.mode == "RGB"


def test_rgba_source_keeps_alpha_for_png_target():
    data = make_image_bytes((100, 100), mode="RGBA", color=(10, 20, 30, 128), fmt="PNG")
    resized = fetch_and_resize_image.resize_image_bytes(data, 600, "PNG")
    with Image.open(io.BytesIO(resized)) as image:
        assert image.mode == "RGBA"


def test_format_for_path_rejects_unsupported_extension():
    try:
        fetch_and_resize_image.format_for_path("picture.bmp")
        assert False, "expected ValueError"
    except ValueError:
        pass


def test_cli_end_to_end(tmp_path):
    source_path = tmp_path / "source.png"
    source_path.write_bytes(make_image_bytes((1200, 900)))
    output_path = tmp_path / "resized.jpg"

    result = subprocess.run(
        [sys.executable, str(SCRIPT), source_path.as_uri(), str(output_path), "600"],
        capture_output=True,
        text=True,
    )

    assert result.returncode == 0, result.stderr
    parsed = json.loads(result.stdout)
    assert parsed == {"path": str(output_path), "width": 600, "height": 450}
    with Image.open(output_path) as image:
        assert image.size == (600, 450)
        assert image.format == "JPEG"


def test_cli_batch_resizes_multiple(tmp_path):
    sources = []
    items = []
    for i, size in enumerate([(1200, 900), (800, 800)]):
        source = tmp_path / f"source{i}.png"
        source.write_bytes(make_image_bytes(size))
        output = tmp_path / f"resized{i}.jpg"
        sources.append((source, output))
        items.append({"url": source.as_uri(), "path": str(output)})

    result = subprocess.run(
        [sys.executable, str(SCRIPT), "--batch"],
        input=json.dumps(items),
        capture_output=True,
        text=True,
    )

    assert result.returncode == 0, result.stderr
    parsed = json.loads(result.stdout)
    assert parsed == [
        {"path": str(sources[0][1]), "width": 600, "height": 450},
        {"path": str(sources[1][1]), "width": 600, "height": 600},
    ]
    for _, output in sources:
        assert output.exists()


def test_cli_batch_reports_per_item_error_without_aborting(tmp_path):
    source = tmp_path / "source.png"
    source.write_bytes(make_image_bytes((1200, 900)))
    good_output = tmp_path / "good.jpg"
    bad_output = tmp_path / "bad.jpg"
    items = [
        {"url": source.as_uri(), "path": str(good_output)},
        {"url": (tmp_path / "does-not-exist.png").as_uri(), "path": str(bad_output)},
    ]

    result = subprocess.run(
        [sys.executable, str(SCRIPT), "--batch"],
        input=json.dumps(items),
        capture_output=True,
        text=True,
    )

    assert result.returncode == 0, result.stderr
    parsed = json.loads(result.stdout)
    assert parsed[0] == {"path": str(good_output), "width": 600, "height": 450}
    assert parsed[1]["path"] == str(bad_output)
    assert "error" in parsed[1]
    assert good_output.exists()
    assert not bad_output.exists()

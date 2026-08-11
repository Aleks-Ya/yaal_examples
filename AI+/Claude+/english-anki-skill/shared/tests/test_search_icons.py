import json
import os
import subprocess
import sys
import threading
from http.server import BaseHTTPRequestHandler, HTTPServer
from pathlib import Path

import search_icons

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "search_icons.py"

SAMPLE_PAYLOAD = {
    "icons": [
        "streamline:ladder",
        "mdi:ladder-truck",
        "ph:ladder-fill",
        "mdi:ladder",
        "carbon:step-ladder",
        "tabler:ladder",
    ],
    "total": 6,
    "collections": {
        "mdi": {"name": "Material Design Icons"},
        "ph": {"name": "Phosphor"},
        "tabler": {"name": "Tabler Icons"},
        "streamline": {"name": "Streamline"},
    },
}

SAMPLE_ICON_SVG = (
    '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">'
    '<path fill="#334155" d="M14 1v2h-4V1H8v21h2v-2h4v2h2V1z"/></svg>'
)


def test_normalize_query_slugifies():
    assert search_icons.normalize_query("  Ladder ") == "ladder"
    assert search_icons.normalize_query("to bat around") == "to-bat-around"


def test_name_matches_accepts_exact_and_style_variants():
    assert search_icons.name_matches("ladder", "ladder")
    assert search_icons.name_matches("ladder-fill", "ladder")
    assert search_icons.name_matches("ladder-24-regular", "ladder")


def test_name_matches_rejects_other_concepts():
    # `ladder-truck` extends the word into a different object; `step-ladder` merely contains it.
    assert not search_icons.name_matches("ladder-truck", "ladder")
    assert not search_icons.name_matches("step-ladder", "ladder")
    assert not search_icons.name_matches("ladder", "")


def test_parse_results_keeps_only_name_matches():
    ids = [c["id"] for c in search_icons.parse_results(SAMPLE_PAYLOAD, "ladder", 8)]
    assert "mdi:ladder-truck" not in ids
    assert "carbon:step-ladder" not in ids
    assert set(ids) == {"mdi:ladder", "tabler:ladder", "streamline:ladder", "ph:ladder-fill"}


def test_parse_results_ranks_exact_names_and_preferred_collections_first():
    candidates = search_icons.parse_results(SAMPLE_PAYLOAD, "ladder", 8)
    # exact names before the `-fill` variant; within exact names, mdi/tabler before streamline
    assert [c["id"] for c in candidates] == [
        "mdi:ladder",
        "tabler:ladder",
        "streamline:ladder",
        "ph:ladder-fill",
    ]
    assert candidates[0] == {
        "id": "mdi:ladder",
        "prefix": "mdi",
        "name": "ladder",
        "collection": "Material Design Icons",
    }


def test_parse_results_respects_limit_and_empty_payloads():
    assert len(search_icons.parse_results(SAMPLE_PAYLOAD, "ladder", 2)) == 2
    assert search_icons.parse_results(SAMPLE_PAYLOAD, "beggar", 8) == []
    assert search_icons.parse_results({}, "ladder", 8) == []


def test_wrap_icon_svg_builds_the_card_around_the_original_viewbox():
    card = search_icons.wrap_icon_svg(SAMPLE_ICON_SVG, "mdi:ladder")
    assert card.startswith(
        '<svg xmlns="http://www.w3.org/2000/svg" width="256" height="256" viewBox="0 0 512 512">'
    )
    assert '<rect width="512" height="512" rx="72" fill="#ffffff"' in card
    # the icon is nested with its own viewBox, so the browser centres/scales it for us
    assert '<svg x="64" y="64" width="384" height="384" viewBox="0 0 24 24"' in card
    assert 'd="M14 1v2h-4V1H8v21h2v-2h4v2h2V1z"' in card
    assert card.endswith("</svg></svg>")


def test_wrap_icon_svg_rejects_unusable_input():
    for bad in ("not an svg", '<svg width="24"><path/></svg>'):
        try:
            search_icons.wrap_icon_svg(bad, "mdi:ladder")
        except ValueError:
            continue
        raise AssertionError(f"expected ValueError for {bad!r}")


def test_wrap_icon_svg_rejects_scripted_svg():
    scripted = '<svg viewBox="0 0 24 24"><script>alert(1)</script></svg>'
    try:
        search_icons.wrap_icon_svg(scripted, "mdi:ladder")
    except ValueError as e:
        assert "script" in str(e)
    else:
        raise AssertionError("expected ValueError for a scripted SVG")


class _StubHandler(BaseHTTPRequestHandler):
    status = 200

    def do_GET(self):
        if self.status != 200:
            self.send_response(self.status)
            self.end_headers()
            self.wfile.write(b"boom")
            return
        if self.path.startswith("/search"):
            body, content_type = json.dumps(SAMPLE_PAYLOAD).encode("utf-8"), "application/json"
        else:
            body, content_type = SAMPLE_ICON_SVG.encode("utf-8"), "image/svg+xml"
        self.send_response(200)
        self.send_header("Content-Type", content_type)
        self.end_headers()
        self.wfile.write(body)

    def log_message(self, *args):
        pass


def _run_cli(status, *args):
    _StubHandler.status = status
    server = HTTPServer(("127.0.0.1", 0), _StubHandler)
    thread = threading.Thread(target=server.serve_forever, daemon=True)
    thread.start()
    try:
        env = {**os.environ, "ICONIFY_API_BASE": f"http://127.0.0.1:{server.server_port}"}
        result = subprocess.run(
            [sys.executable, str(SCRIPT), *args],
            capture_output=True,
            text=True,
            env=env,
        )
    finally:
        server.shutdown()
        _StubHandler.status = 200
    return result


def test_cli_search_end_to_end():
    result = _run_cli(200, "ladder", "--limit", "3")
    assert result.returncode == 0, result.stderr
    parsed = json.loads(result.stdout)
    assert [c["id"] for c in parsed] == ["mdi:ladder", "tabler:ladder", "streamline:ladder"]


def test_cli_search_with_no_match_prints_empty_array():
    result = _run_cli(200, "beggar")
    assert result.returncode == 0, result.stderr
    assert json.loads(result.stdout) == []


def test_cli_fetch_writes_the_card_svg(tmp_path):
    output = tmp_path / "ladder-noun-icon.svg"
    result = _run_cli(200, "--fetch", "mdi:ladder", str(output))
    assert result.returncode == 0, result.stderr
    assert json.loads(result.stdout) == {"path": str(output), "id": "mdi:ladder"}
    card = output.read_text(encoding="utf-8")
    assert 'width="256" height="256" viewBox="0 0 512 512"' in card
    assert 'fill="#ffffff"' in card


def test_cli_surfaces_http_error():
    result = _run_cli(500, "ladder")
    assert result.returncode == 1
    assert "500" in result.stderr

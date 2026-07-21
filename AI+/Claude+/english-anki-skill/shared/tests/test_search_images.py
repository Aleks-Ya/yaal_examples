import json
import os
import subprocess
import sys
import threading
from http.server import BaseHTTPRequestHandler, HTTPServer
from pathlib import Path

import search_images

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "search_images.py"

SAMPLE_PAYLOAD = {
    "result_count": 2,
    "results": [
        {
            "url": "https://live.staticflickr.com/1/beggar.jpg",
            "thumbnail": "https://api.openverse.org/v1/images/aaa/thumb/",
            "title": "Little beggar",
            "tags": [{"name": "beggar"}, {"name": "poverty"}, {"no_name": "x"}],
            "source": "flickr",
            "license": "by-nc-sa",
        },
        {
            "url": "https://live.staticflickr.com/2/street.jpg",
            "thumbnail": "https://api.openverse.org/v1/images/bbb/thumb/",
            "title": "Beggar on street",
            "tags": [],
            "source": "flickr",
            "license": "by",
        },
    ],
}


def test_parse_results_extracts_fields_and_flattens_tags():
    candidates = search_images.parse_results(SAMPLE_PAYLOAD, 8)
    assert candidates[0] == {
        "url": "https://live.staticflickr.com/1/beggar.jpg",
        "thumbnail": "https://api.openverse.org/v1/images/aaa/thumb/",
        "title": "Little beggar",
        "tags": ["beggar", "poverty"],
        "source": "flickr",
        "license": "by-nc-sa",
    }


def test_parse_results_respects_limit():
    assert len(search_images.parse_results(SAMPLE_PAYLOAD, 1)) == 1


def test_parse_results_empty_on_no_results():
    assert search_images.parse_results({"results": []}, 8) == []
    assert search_images.parse_results({}, 8) == []


class _StubHandler(BaseHTTPRequestHandler):
    status = 200

    def do_GET(self):
        if self.status == 200:
            body = json.dumps(SAMPLE_PAYLOAD).encode("utf-8")
        else:
            body = json.dumps({"detail": "boom"}).encode("utf-8")
        self.send_response(self.status)
        self.send_header("Content-Type", "application/json")
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
        env = {
            **os.environ,
            "OPENVERSE_API_BASE": f"http://127.0.0.1:{server.server_port}",
        }
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


def test_cli_end_to_end():
    result = _run_cli(200, "beggar", "--limit", "8")
    assert result.returncode == 0, result.stderr
    parsed = json.loads(result.stdout)
    assert [c["title"] for c in parsed] == ["Little beggar", "Beggar on street"]
    assert parsed[0]["tags"] == ["beggar", "poverty"]


def test_cli_surfaces_http_error():
    result = _run_cli(500, "beggar")
    assert result.returncode == 1
    assert "boom" in result.stderr

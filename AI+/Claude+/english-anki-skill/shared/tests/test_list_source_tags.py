import json
import subprocess
import sys
import threading
from http.server import BaseHTTPRequestHandler, HTTPServer
from pathlib import Path

import list_source_tags

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "list_source_tags.py"


def test_filter_source_tags_keeps_only_source_prefix_and_sorts():
    tags = [
        "source::movie::the-guard",
        "en::parts::noun",
        "source::blog::jerome-tan",
        "source::company",
    ]
    assert list_source_tags.filter_source_tags(tags) == [
        "source::blog::jerome-tan",
        "source::company",
        "source::movie::the-guard",
    ]


def test_filter_source_tags_excludes_mid_hierarchy_source_segment():
    # A `source::` segment buried deeper in a tag is NOT a source tag.
    tags = [
        "it::big-data::spark::streaming::structured::source::kafka",
        "source::movie::backrooms",
    ]
    assert list_source_tags.filter_source_tags(tags) == ["source::movie::backrooms"]


def test_filter_source_tags_empty_input():
    assert list_source_tags.filter_source_tags([]) == []


class _AnkiConnectStub(BaseHTTPRequestHandler):
    tags = []
    requests_seen = []

    def do_POST(self):
        length = int(self.headers["Content-Length"])
        request = json.loads(self.rfile.read(length))
        type(self).requests_seen.append(request)
        result = self.tags if request["action"] == "getTags" else None
        body = json.dumps({"result": result, "error": None}).encode("utf-8")
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.end_headers()
        self.wfile.write(body)

    def log_message(self, *args):
        pass


def _run_cli(tags):
    _AnkiConnectStub.tags = tags
    _AnkiConnectStub.requests_seen = []
    server = HTTPServer(("127.0.0.1", 0), _AnkiConnectStub)
    thread = threading.Thread(target=server.serve_forever, daemon=True)
    thread.start()
    try:
        env = {
            **__import__("os").environ,
            "ANKICONNECT_URL": f"http://127.0.0.1:{server.server_port}",
        }
        result = subprocess.run(
            [sys.executable, str(SCRIPT)],
            capture_output=True,
            text=True,
            env=env,
        )
    finally:
        server.shutdown()
    return result


def test_cli_end_to_end_returns_filtered_sorted_source_tags():
    result = _run_cli(
        [
            "source::movie::the-guard",
            "en::parts::noun",
            "source::movie::backrooms",
            "it::big-data::spark::streaming::structured::source::kafka",
            "source::company",
        ]
    )
    assert result.returncode == 0, result.stderr
    assert json.loads(result.stdout) == {
        "source_tags": [
            "source::company",
            "source::movie::backrooms",
            "source::movie::the-guard",
        ]
    }
    assert [r["action"] for r in _AnkiConnectStub.requests_seen] == ["getTags"]


def test_cli_unreachable_anki_exits_1():
    env = {
        **__import__("os").environ,
        "ANKICONNECT_URL": "http://127.0.0.1:1",  # nothing listens here
    }
    result = subprocess.run(
        [sys.executable, str(SCRIPT)],
        capture_output=True,
        text=True,
        env=env,
    )
    assert result.returncode == 1
    assert "error" in result.stderr

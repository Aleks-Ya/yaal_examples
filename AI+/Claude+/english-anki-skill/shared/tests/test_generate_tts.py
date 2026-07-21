import base64
import json
import subprocess
import sys
import threading
from http.server import BaseHTTPRequestHandler, HTTPServer
from pathlib import Path

import generate_tts

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "generate_tts.py"


def test_build_payload_uses_google_tts_shape():
    payload = generate_tts.build_payload("hello", "en-US-Wavenet-B", "en-US", "MALE", 1.0, 0.0)
    assert payload == {
        "input": {"text": "hello"},
        "voice": {"languageCode": "en-US", "name": "en-US-Wavenet-B", "ssmlGender": "MALE"},
        "audioConfig": {"audioEncoding": "MP3", "speakingRate": 1.0, "pitch": 0.0},
    }


def test_decode_audio_content_round_trips_base64():
    raw = b"fake-mp3-bytes"
    response = {"audioContent": base64.b64encode(raw).decode("ascii")}
    assert generate_tts.decode_audio_content(response) == raw


def test_decode_audio_content_raises_on_error_payload():
    response = {"error": {"message": "API key not valid"}}
    try:
        generate_tts.decode_audio_content(response)
        assert False, "expected ValueError"
    except ValueError as e:
        assert "API key not valid" in str(e)


def test_decode_audio_content_raises_on_missing_field():
    try:
        generate_tts.decode_audio_content({})
        assert False, "expected ValueError"
    except ValueError:
        pass


def test_strip_html_removes_tags_and_unescapes_entities():
    assert generate_tts.strip_html("a <b>record</b>&nbsp;&amp; more") == "a record & more"


class _StubHandler(BaseHTTPRequestHandler):
    audio_bytes = b"stub-mp3"
    status = 200

    def do_POST(self):
        length = int(self.headers["Content-Length"])
        self.rfile.read(length)
        if self.status == 200:
            body = json.dumps(
                {"audioContent": base64.b64encode(self.audio_bytes).decode("ascii")}
            ).encode("utf-8")
        else:
            body = json.dumps({"error": {"code": self.status, "message": "boom"}}).encode("utf-8")
        self.send_response(self.status)
        self.send_header("Content-Type", "application/json")
        self.end_headers()
        self.wfile.write(body)

    def log_message(self, *args):
        pass


def _run_cli(tmp_path, status):
    _StubHandler.status = status
    server = HTTPServer(("127.0.0.1", 0), _StubHandler)
    thread = threading.Thread(target=server.serve_forever, daemon=True)
    thread.start()
    try:
        key_file = tmp_path / "api_key.txt"
        key_file.write_text("test-key\n")
        output_path = tmp_path / "out.mp3"
        env = {
            **__import__("os").environ,
            "GOOGLE_TTS_ENDPOINT": f"http://127.0.0.1:{server.server_port}/text:synthesize",
            "GOOGLE_TTS_API_KEY_FILE": str(key_file),
        }
        result = subprocess.run(
            [sys.executable, str(SCRIPT), "a <b>record</b>", str(output_path)],
            capture_output=True,
            text=True,
            env=env,
        )
    finally:
        server.shutdown()
        _StubHandler.status = 200
    return result, output_path


def test_cli_surfaces_api_error_message(tmp_path):
    result, output_path = _run_cli(tmp_path, status=403)
    assert result.returncode == 1
    assert "boom" in result.stderr
    assert not output_path.exists()


def test_cli_end_to_end(tmp_path, monkeypatch):
    server = HTTPServer(("127.0.0.1", 0), _StubHandler)
    thread = threading.Thread(target=server.serve_forever, daemon=True)
    thread.start()
    try:
        key_file = tmp_path / "api_key.txt"
        key_file.write_text("test-key\n")
        output_path = tmp_path / "out.mp3"

        env = {
            **__import__("os").environ,
            "GOOGLE_TTS_ENDPOINT": f"http://127.0.0.1:{server.server_port}/text:synthesize",
            "GOOGLE_TTS_API_KEY_FILE": str(key_file),
        }
        result = subprocess.run(
            [sys.executable, str(SCRIPT), "a <b>record</b>", str(output_path)],
            capture_output=True,
            text=True,
            env=env,
        )
    finally:
        server.shutdown()

    assert result.returncode == 0, result.stderr
    assert output_path.read_bytes() == _StubHandler.audio_bytes
    parsed = json.loads(result.stdout)
    assert parsed == {"path": str(output_path), "bytes": len(_StubHandler.audio_bytes)}


def test_cli_batch_mixes_successes_and_per_item_errors(tmp_path):
    server = HTTPServer(("127.0.0.1", 0), _StubHandler)
    thread = threading.Thread(target=server.serve_forever, daemon=True)
    thread.start()
    try:
        key_file = tmp_path / "api_key.txt"
        key_file.write_text("test-key\n")
        english_path = tmp_path / "english.mp3"
        definition_path = tmp_path / "definition.mp3"

        items = [
            {"text": "a <b>record</b>", "path": str(english_path)},
            {"text": "a short definition", "path": str(definition_path)},
            {"path": str(tmp_path / "broken.mp3")},  # no "text" -> per-item error
        ]
        env = {
            **__import__("os").environ,
            "GOOGLE_TTS_ENDPOINT": f"http://127.0.0.1:{server.server_port}/text:synthesize",
            "GOOGLE_TTS_API_KEY_FILE": str(key_file),
        }
        result = subprocess.run(
            [sys.executable, str(SCRIPT), "--batch"],
            input=json.dumps(items),
            capture_output=True,
            text=True,
            env=env,
        )
    finally:
        server.shutdown()

    assert result.returncode == 0, result.stderr
    parsed = json.loads(result.stdout)
    assert parsed[0] == {"path": str(english_path), "bytes": len(_StubHandler.audio_bytes)}
    assert parsed[1] == {"path": str(definition_path), "bytes": len(_StubHandler.audio_bytes)}
    assert parsed[2]["error"]
    assert english_path.read_bytes() == _StubHandler.audio_bytes
    assert definition_path.read_bytes() == _StubHandler.audio_bytes


def test_cli_batch_exits_1_without_api_key_file(tmp_path):
    env = {
        **__import__("os").environ,
        "GOOGLE_TTS_API_KEY_FILE": str(tmp_path / "missing_key.txt"),
    }
    result = subprocess.run(
        [sys.executable, str(SCRIPT), "--batch"],
        input="[]",
        capture_output=True,
        text=True,
        env=env,
    )
    assert result.returncode == 1
    assert "error" in result.stderr

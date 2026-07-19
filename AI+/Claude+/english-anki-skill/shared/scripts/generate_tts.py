#!/usr/bin/env python3
"""Synthesize an mp3 from text with Google Cloud Text-to-Speech, before storing in Anki.

Usage: generate_tts.py <text> <output_path.mp3>
           [--voice NAME] [--language CODE] [--gender GENDER] [--rate R] [--pitch P]

- `text` is the text to speak; any HTML is stripped before synthesis.
- `output_path`'s extension is not inspected — the response is always MP3 (audioEncoding=MP3).
- Voice defaults reproduce the HyperTTS "English (US), Male, en-US-Wavenet-B (Google)" preset:
  `--voice en-US-Wavenet-B --language en-US --gender MALE --rate 1.0 --pitch 0.0`.

The Google API key is read from `/home/aleks/.gcp/tts_api_key.txt` (override via the
`GOOGLE_TTS_API_KEY_FILE` env var) and sent as `?key=` on the request URL. The endpoint is
`https://texttospeech.googleapis.com/v1/text:synthesize` (override via `GOOGLE_TTS_ENDPOINT`,
handy for tests).

Prints a JSON object to stdout: {"path": ..., "bytes": N}.
On request/decode failure, prints an error to stderr and exits 1.
"""
import argparse
import base64
import html
import json
import os
import re
import sys
import urllib.error
import urllib.request

DEFAULT_VOICE = "en-US-Wavenet-B"
DEFAULT_LANGUAGE = "en-US"
DEFAULT_GENDER = "MALE"
DEFAULT_RATE = 1.0
DEFAULT_PITCH = 0.0

DEFAULT_ENDPOINT = "https://texttospeech.googleapis.com/v1/text:synthesize"
DEFAULT_API_KEY_FILE = "/home/aleks/.gcp/tts_api_key.txt"

TAG_RE = re.compile(r"<[^>]+>")


def strip_html(text):
    """Drop tags, unescape HTML entities, and collapse whitespace so nothing is read aloud raw."""
    return re.sub(r"\s+", " ", html.unescape(TAG_RE.sub(" ", text))).strip()


def build_payload(text, voice, language, gender, rate, pitch):
    return {
        "input": {"text": text},
        "voice": {"languageCode": language, "name": voice, "ssmlGender": gender},
        "audioConfig": {"audioEncoding": "MP3", "speakingRate": rate, "pitch": pitch},
    }


def decode_audio_content(response_json):
    """Return the raw mp3 bytes from a synthesize response, raising on an API error payload."""
    if "error" in response_json:
        message = response_json["error"].get("message", response_json["error"])
        raise ValueError(f"API error: {message}")
    try:
        audio_content = response_json["audioContent"]
    except KeyError:
        raise ValueError("response has no 'audioContent' field") from None
    return base64.b64decode(audio_content)


def read_api_key():
    path = os.environ.get("GOOGLE_TTS_API_KEY_FILE", DEFAULT_API_KEY_FILE)
    with open(path) as f:
        return f.read().strip()


def synthesize(payload, api_key):
    endpoint = os.environ.get("GOOGLE_TTS_ENDPOINT", DEFAULT_ENDPOINT)
    url = f"{endpoint}?key={api_key}"
    request = urllib.request.Request(
        url,
        data=json.dumps(payload).encode("utf-8"),
        headers={"Content-Type": "application/json"},
        method="POST",
    )
    try:
        with urllib.request.urlopen(request) as response:
            response_json = json.load(response)
    except urllib.error.HTTPError as e:
        # Surface Google's structured error message (e.g. bad key / IP restriction) rather than the
        # opaque "HTTP Error 403: Forbidden" that HTTPError stringifies to. Never echo `api_key`.
        body = e.read().decode("utf-8", "replace")
        try:
            message = json.loads(body).get("error", {}).get("message")
        except (json.JSONDecodeError, AttributeError):
            message = None
        raise ValueError(f"HTTP {e.code}: {message or body.strip() or e.reason}") from None
    return decode_audio_content(response_json)


def parse_args(argv):
    parser = argparse.ArgumentParser(description="Synthesize an mp3 with Google Cloud TTS.")
    parser.add_argument("text")
    parser.add_argument("output_path")
    parser.add_argument("--voice", default=DEFAULT_VOICE)
    parser.add_argument("--language", default=DEFAULT_LANGUAGE)
    parser.add_argument("--gender", default=DEFAULT_GENDER)
    parser.add_argument("--rate", type=float, default=DEFAULT_RATE)
    parser.add_argument("--pitch", type=float, default=DEFAULT_PITCH)
    return parser.parse_args(argv)


def main(argv=None):
    args = parse_args(argv)

    try:
        text = strip_html(args.text)
        payload = build_payload(text, args.voice, args.language, args.gender, args.rate, args.pitch)
        audio = synthesize(payload, read_api_key())
    except Exception as e:
        print(f"error: {e}", file=sys.stderr)
        sys.exit(1)

    with open(args.output_path, "wb") as f:
        f.write(audio)

    print(json.dumps({"path": args.output_path, "bytes": len(audio)}))


if __name__ == "__main__":
    main()

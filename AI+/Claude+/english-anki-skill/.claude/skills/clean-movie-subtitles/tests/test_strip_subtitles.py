import json
import subprocess
import sys
from pathlib import Path

import strip_subtitles

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "strip_subtitles.py"

SRT_SAMPLE = """\
1
00:00:01,000 --> 00:00:03,500
<i>What the f*ck</i>
was that?

2
00:00:04,000 --> 00:00:06,000
[ominous music]
JOHN: Get down!
"""


def test_detect_format():
    assert strip_subtitles.detect_format(SRT_SAMPLE) == "srt"
    assert strip_subtitles.detect_format("Just plain dialogue.\nNo timestamps here.") == "plain"


def test_strip_srt_removes_indices_timestamps_and_tags():
    out = strip_subtitles.strip(SRT_SAMPLE)
    lines = out.splitlines()
    assert lines == [
        "What the f*ck",
        "was that?",
        "[ominous music]",
        "JOHN: Get down!",
    ]
    # no cue index, timestamp arrow, or <i> tag survives
    assert "-->" not in out
    assert "<i>" not in out
    assert "1" not in lines and "2" not in lines


def test_keeps_sound_cues_and_speaker_labels():
    out = strip_subtitles.strip(SRT_SAMPLE)
    assert "[ominous music]" in out  # sound cue kept
    assert "JOHN: Get down!" in out  # speaker label kept (Claude removes it later, not the script)


def test_plain_text_leading_and_standalone_timestamps():
    text = "\n".join([
        "[00:01:23] I told you not to come here.",
        "00:02:00,500 So this is the place.",
        "12:30",  # standalone timestamp-only line
        "It's 1:23 and we are still waiting.",  # bare M:SS in dialogue must survive
    ])
    out = strip_subtitles.strip(text).splitlines()
    assert out[0] == "I told you not to come here."       # bracketed prefix stripped
    assert "So this is the place." in out                 # HH:MM:SS,ms prefix stripped
    assert "12:30" not in out                              # standalone timestamp removed
    assert "It's 1:23 and we are still waiting." in out    # M:SS in dialogue preserved


def test_plain_text_without_timestamps_only_drops_tags_and_empties():
    text = "First <b>bold</b> line.\n\n   \nSecond line."
    assert strip_subtitles.strip(text).splitlines() == ["First bold line.", "Second line."]


def test_brace_override_blocks_removed():
    assert strip_subtitles.strip("{\\an8}Top text") == "Top text"


def test_derive_output_path_variants():
    assert strip_subtitles.derive_output_path("/tmp/Backrooms 2026.txt") == "/tmp/Backrooms 2026 clean.txt"
    assert strip_subtitles.derive_output_path("/tmp/movie.srt") == "/tmp/movie clean.srt"
    assert strip_subtitles.derive_output_path("/tmp/no_ext") == "/tmp/no_ext clean"
    assert strip_subtitles.derive_output_path("sub.en.srt") == "sub.en clean.srt"


def test_cli_end_to_end(tmp_path):
    src = tmp_path / "Backrooms 2026.txt"
    src.write_text(SRT_SAMPLE, encoding="utf-8")
    result = subprocess.run(
        [sys.executable, str(SCRIPT), str(src)],
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    payload = json.loads(result.stdout)
    assert payload["format"] == "srt"
    assert payload["output_path"] == str(tmp_path / "Backrooms 2026 clean.txt")
    assert payload["text"].splitlines() == [
        "What the f*ck",
        "was that?",
        "[ominous music]",
        "JOHN: Get down!",
    ]
    assert payload["input_lines"] == len(SRT_SAMPLE.splitlines())
    assert payload["output_lines"] == 4


def test_cli_missing_path_exits_2(tmp_path):
    result = subprocess.run(
        [sys.executable, str(SCRIPT), str(tmp_path / "nope.srt")],
        capture_output=True,
        text=True,
    )
    assert result.returncode == 2
    assert "error" in json.loads(result.stderr)

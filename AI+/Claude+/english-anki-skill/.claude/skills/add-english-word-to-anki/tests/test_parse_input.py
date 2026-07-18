import json
import subprocess
import sys
from pathlib import Path

import parse_input

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "parse_input.py"


def write(tmp_path, text):
    path = tmp_path / "input.md"
    path.write_text(text, encoding="utf-8")
    return path


def test_parses_single_marked_word(tmp_path):
    path = write(tmp_path, "Just _pin_ a medal to me body.\n")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert entries == [
        {"line": 1, "word": "pin", "sentence": "Just pin a medal to me body."}
    ]


def test_parses_multi_word_phrase(tmp_path):
    path = write(tmp_path, "We'll be _casting off_ as soon as you two are ashore.\n")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert entries[0]["word"] == "casting off"
    assert entries[0]["sentence"] == "We'll be casting off as soon as you two are ashore."


def test_skips_blank_lines(tmp_path):
    path = write(tmp_path, "\n   \nJust _pin_ a medal to me body.\n\n")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert len(entries) == 1
    assert entries[0]["line"] == 3


def test_multiple_valid_lines_numbered_correctly(tmp_path):
    path = write(
        tmp_path,
        "Just _pin_ a medal to me body.\n"
        "Look, I know that you've had a lot of fun _batting_ around the American.\n"
        "They're eating you alive, the _beggars_.\n",
    )
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert [e["line"] for e in entries] == [1, 2, 3]
    assert [e["word"] for e in entries] == ["pin", "batting", "beggars"]


def test_reports_line_with_no_marker(tmp_path):
    path = write(tmp_path, "This line has no marker.\n")
    entries, errors = parse_input.parse(path)
    assert entries == []
    assert len(errors) == 1
    assert "line 1" in errors[0]
    assert "no word marked" in errors[0]


def test_reports_line_with_multiple_markers(tmp_path):
    path = write(tmp_path, "This has _two_ _markers_.\n")
    entries, errors = parse_input.parse(path)
    assert entries == []
    assert len(errors) == 1
    assert "line 1" in errors[0]
    assert "multiple words marked" in errors[0]


def test_valid_and_invalid_lines_mixed(tmp_path):
    path = write(
        tmp_path,
        "Just _pin_ a medal to me body.\n"
        "This line has no marker.\n"
        "This has _two_ _markers_.\n",
    )
    entries, errors = parse_input.parse(path)
    assert [e["word"] for e in entries] == ["pin"]
    assert len(errors) == 2
    assert "line 2" in errors[0]
    assert "line 3" in errors[1]


def test_cli_success_prints_json_and_exits_zero(tmp_path):
    path = write(tmp_path, "Just _pin_ a medal to me body.\n")
    result = subprocess.run(
        [sys.executable, str(SCRIPT), str(path)], capture_output=True, text=True
    )
    assert result.returncode == 0
    parsed = json.loads(result.stdout)
    assert parsed == [{"line": 1, "word": "pin", "sentence": "Just pin a medal to me body."}]


def test_cli_failure_prints_errors_and_exits_nonzero(tmp_path):
    path = write(tmp_path, "This line has no marker.\n")
    result = subprocess.run(
        [sys.executable, str(SCRIPT), str(path)], capture_output=True, text=True
    )
    assert result.returncode == 1
    assert result.stdout == ""
    assert "no word marked" in result.stderr

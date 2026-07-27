import json
import subprocess
import sys
from pathlib import Path

import parse_input

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "parse_input.py"


def write(tmp_path, text, name="input.md"):
    path = tmp_path / name
    path.write_text(text, encoding="utf-8")
    return path


# --- parse mode ---


def test_parses_single_marked_word(tmp_path):
    path = write(tmp_path, "# The Guard\nJust _pin_ a medal to me body.\n")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert entries == [
        {
            "source": "The Guard",
            "line": 2,
            "word": "pin",
            "sentence": "Just pin a medal to me body.",
        }
    ]


def test_parses_multi_word_phrase(tmp_path):
    path = write(
        tmp_path,
        "# The Guard\nWe'll be _casting off_ as soon as you two are ashore.\n",
    )
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert entries[0]["word"] == "casting off"
    assert entries[0]["sentence"] == "We'll be casting off as soon as you two are ashore."


def test_source_derived_from_header(tmp_path):
    path = write(tmp_path, "# The Guard 2011\nJust _pin_ a medal.\n")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert entries[0]["source"] == "The Guard 2011"
    assert "file" not in entries[0]


def test_no_source_header_yields_null_source(tmp_path):
    path = write(tmp_path, "# NO_SOURCE\nJust _pin_ a medal.\n")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert entries[0]["source"] is None


def test_multiple_sections_each_carry_their_source(tmp_path):
    path = write(
        tmp_path,
        "# NO_SOURCE\n"
        "Her ability to find a _decent_ job.\n"
        "\n"
        "# The Guard 2011\n"
        "They're eating you alive, the _beggars_.\n",
    )
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert [(e["source"], e["word"], e["line"]) for e in entries] == [
        (None, "decent", 2),
        ("The Guard 2011", "beggars", 5),
    ]


def test_empty_section_produces_no_entries_and_no_error(tmp_path):
    path = write(
        tmp_path,
        "# Python\n\n# The Guard\nJust _pin_ a medal.\n",
    )
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert [e["source"] for e in entries] == ["The Guard"]


def test_skips_blank_lines(tmp_path):
    path = write(tmp_path, "# The Guard\n\n   \nJust _pin_ a medal to me body.\n\n")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert len(entries) == 1
    assert entries[0]["line"] == 4


def test_multiple_valid_lines_numbered_correctly(tmp_path):
    path = write(
        tmp_path,
        "# The Guard\n"
        "Just _pin_ a medal to me body.\n"
        "Look, I know that you've had a lot of fun _batting_ around the American.\n"
        "They're eating you alive, the _beggars_.\n",
    )
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert [e["line"] for e in entries] == [2, 3, 4]
    assert [e["word"] for e in entries] == ["pin", "batting", "beggars"]


def test_reports_line_with_no_marker(tmp_path):
    path = write(tmp_path, "# The Guard\nThis line has no marker.\n")
    entries, errors = parse_input.parse(path)
    assert entries == []
    assert len(errors) == 1
    assert "line 2" in errors[0]
    assert "no word marked" in errors[0]


def test_reports_line_with_multiple_markers(tmp_path):
    path = write(tmp_path, "# The Guard\nThis has _two_ _markers_.\n")
    entries, errors = parse_input.parse(path)
    assert entries == []
    assert len(errors) == 1
    assert "line 2" in errors[0]
    assert "multiple words marked" in errors[0]


def test_valid_and_invalid_lines_mixed(tmp_path):
    path = write(
        tmp_path,
        "# The Guard\n"
        "Just _pin_ a medal to me body.\n"
        "This line has no marker.\n"
        "This has _two_ _markers_.\n",
    )
    entries, errors = parse_input.parse(path)
    assert [e["word"] for e in entries] == ["pin"]
    assert len(errors) == 2
    assert "line 3" in errors[0]
    assert "line 4" in errors[1]


def test_sentence_before_any_header_is_error(tmp_path):
    path = write(tmp_path, "A _stray_ sentence.\n# The Guard\nJust _pin_ a medal.\n")
    entries, errors = parse_input.parse(path)
    assert [e["word"] for e in entries] == ["pin"]
    assert len(errors) == 1
    assert "line 1" in errors[0]
    assert "before any" in errors[0]


def test_malformed_header_is_error(tmp_path):
    path = write(tmp_path, "## Sub heading\nJust _pin_ a medal.\n")
    entries, errors = parse_input.parse(path)
    assert entries == []
    assert len(errors) >= 1
    assert "line 1" in errors[0]
    assert "malformed source header" in errors[0]


# --- clear mode ---


def test_clear_removes_given_lines_keeping_headers(tmp_path):
    path = write(
        tmp_path,
        "# The Guard\n"  # 1
        "Just _pin_ a medal.\n"  # 2
        "\n"  # 3
        "# Python\n"  # 4
        "A _stray_ line.\n",  # 5
    )
    removed = parse_input.clear(path, [2, 5])
    assert removed == 2
    assert path.read_text(encoding="utf-8") == "# The Guard\n\n# Python\n"


def test_clear_no_lines_is_noop(tmp_path):
    original = "# The Guard\nJust _pin_ a medal.\n"
    path = write(tmp_path, original)
    removed = parse_input.clear(path, [])
    assert removed == 0
    assert path.read_text(encoding="utf-8") == original


# --- CLI ---


def test_cli_success_prints_json_object_and_exits_zero(tmp_path):
    write(
        tmp_path,
        "# The Guard\nJust _pin_ a medal to me body.\n\n# Python\n",
        name="words.md",
    )
    result = subprocess.run(
        [sys.executable, str(SCRIPT), str(tmp_path / "words.md")],
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    parsed = json.loads(result.stdout)
    assert parsed == {
        "entries": [
            {
                "source": "The Guard",
                "line": 2,
                "word": "pin",
                "sentence": "Just pin a medal to me body.",
            }
        ]
    }


def test_cli_failure_prints_errors_and_exits_nonzero(tmp_path):
    path = write(tmp_path, "# The Guard\nThis line has no marker.\n")
    result = subprocess.run(
        [sys.executable, str(SCRIPT), str(path)], capture_output=True, text=True
    )
    assert result.returncode == 1
    assert result.stdout == ""
    assert "no word marked" in result.stderr


def test_cli_bad_path_exits_two(tmp_path):
    result = subprocess.run(
        [sys.executable, str(SCRIPT), str(tmp_path / "does-not-exist")],
        capture_output=True,
        text=True,
    )
    assert result.returncode == 2


def test_cli_clear_mode_removes_lines(tmp_path):
    path = write(
        tmp_path,
        "# The Guard\nJust _pin_ a medal.\nAnother _lad_ ran.\n",
    )
    result = subprocess.run(
        [sys.executable, str(SCRIPT), "--clear", str(path)],
        input=json.dumps({"remove_lines": [2]}),
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    assert json.loads(result.stdout) == {"removed": 1}
    assert path.read_text(encoding="utf-8") == "# The Guard\nAnother _lad_ ran.\n"

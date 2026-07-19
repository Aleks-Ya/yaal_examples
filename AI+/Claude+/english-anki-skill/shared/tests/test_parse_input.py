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


def test_parses_single_marked_word(tmp_path):
    path = write(tmp_path, "Just _pin_ a medal to me body.\n")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert entries == [
        {
            "source": "input",
            "file": "input.md",
            "line": 1,
            "word": "pin",
            "sentence": "Just pin a medal to me body.",
        }
    ]


def test_parses_multi_word_phrase(tmp_path):
    path = write(tmp_path, "We'll be _casting off_ as soon as you two are ashore.\n")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert entries[0]["word"] == "casting off"
    assert entries[0]["sentence"] == "We'll be casting off as soon as you two are ashore."


def test_source_derived_from_file_name(tmp_path):
    path = write(tmp_path, "Just _pin_ a medal.\n", name="The Guard 2011.md")
    entries, errors = parse_input.parse(path)
    assert errors == []
    assert entries[0]["source"] == "The Guard 2011"
    assert entries[0]["file"] == "The Guard 2011.md"


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
    assert "input.md line 1" in errors[0]
    assert "no word marked" in errors[0]


def test_reports_line_with_multiple_markers(tmp_path):
    path = write(tmp_path, "This has _two_ _markers_.\n")
    entries, errors = parse_input.parse(path)
    assert entries == []
    assert len(errors) == 1
    assert "input.md line 1" in errors[0]
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


# --- folder handling (collect) ---


def test_collect_single_file(tmp_path):
    path = write(tmp_path, "Just _pin_ a medal.\n", name="The Guard.md")
    entries, skipped, errors = parse_input.collect(path)
    assert errors == []
    assert skipped == []
    assert [e["source"] for e in entries] == ["The Guard"]


def test_collect_folder_multiple_files_sorted_with_per_file_source(tmp_path):
    write(tmp_path, "Just _pin_ a medal.\n", name="B movie.md")
    write(tmp_path, "The _beggars_ ran.\n", name="A book.txt")
    entries, skipped, errors = parse_input.collect(tmp_path)
    assert errors == []
    assert skipped == []
    # sorted by file name: "A book.txt" before "B movie.md"
    assert [e["source"] for e in entries] == ["A book", "B movie"]
    assert [e["word"] for e in entries] == ["beggars", "pin"]


def test_collect_skips_empty_and_whitespace_only_files(tmp_path):
    write(tmp_path, "Just _pin_ a medal.\n", name="Good.md")
    write(tmp_path, "", name="Empty.md")
    write(tmp_path, "   \n\n\t\n", name="Whitespace.md")
    entries, skipped, errors = parse_input.collect(tmp_path)
    assert errors == []
    assert [e["source"] for e in entries] == ["Good"]
    assert sorted(s["file"] for s in skipped) == ["Empty.md", "Whitespace.md"]
    assert all(s["reason"] == "empty" for s in skipped)


def test_collect_ignores_non_md_txt_and_dotfiles(tmp_path):
    write(tmp_path, "Just _pin_ a medal.\n", name="Good.md")
    write(tmp_path, "not _input_ material.\n", name="image.png")
    write(tmp_path, "hidden _stuff_.\n", name=".hidden.md")
    entries, skipped, errors = parse_input.collect(tmp_path)
    assert errors == []
    assert [e["source"] for e in entries] == ["Good"]


def test_collect_bad_line_reports_with_file_name(tmp_path):
    write(tmp_path, "Just _pin_ a medal.\n", name="Good.md")
    write(tmp_path, "no marker here.\n", name="Bad.md")
    entries, skipped, errors = parse_input.collect(tmp_path)
    assert len(errors) == 1
    assert "Bad.md line 1" in errors[0]


def test_cli_success_prints_json_object_and_exits_zero(tmp_path):
    write(tmp_path, "Just _pin_ a medal to me body.\n", name="The Guard.md")
    write(tmp_path, "", name="Novartis.md")
    result = subprocess.run(
        [sys.executable, str(SCRIPT), str(tmp_path)], capture_output=True, text=True
    )
    assert result.returncode == 0
    parsed = json.loads(result.stdout)
    assert parsed["entries"] == [
        {
            "source": "The Guard",
            "file": "The Guard.md",
            "line": 1,
            "word": "pin",
            "sentence": "Just pin a medal to me body.",
        }
    ]
    assert parsed["skipped"] == [{"file": "Novartis.md", "reason": "empty"}]


def test_cli_failure_prints_errors_and_exits_nonzero(tmp_path):
    path = write(tmp_path, "This line has no marker.\n")
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

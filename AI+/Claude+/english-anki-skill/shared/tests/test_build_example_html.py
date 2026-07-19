import json
import subprocess
import sys
from pathlib import Path

import build_example_html

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "build_example_html.py"


def test_fresh_note_produces_single_item_list():
    result = build_example_html.build(
        None,
        "predictable",
        "When skills don't work as expected, the problem usually falls into a few predictable categories.",
        "The Guard 2011",
    )
    assert result == {
        "html": (
            "<ul><li>When skills don't work as expected, the problem usually falls into "
            "a few <b>predictable</b> categories. (The Guard 2011)</li></ul>"
        ),
        "changed": True,
        "already_present": False,
    }


def test_fresh_note_without_source_omits_suffix():
    result = build_example_html.build(None, "pin", "Just pin a medal to me body.", None)
    assert result["html"] == "<ul><li>Just <b>pin</b> a medal to me body.</li></ul>"
    assert result["changed"] is True


def test_appends_to_legacy_plain_text_note():
    existing = "You've won again, you lucky <b>beggar</b>."
    result = build_example_html.build(
        existing,
        "beggars",
        "They're eating you alive, the beggars.",
        "The Guard 2011",
    )
    assert result["html"] == (
        "<ul><li>You've won again, you lucky <b>beggar</b>.</li>"
        "<li>They're eating you alive, the <b>beggars</b>. (The Guard 2011)</li></ul>"
    )
    assert result["changed"] is True
    assert result["already_present"] is False


def test_appends_to_existing_list():
    existing = "<ul><li>First sentence. (Source A)</li></ul>"
    result = build_example_html.build(
        existing, "word", "Second word sentence.", "Source B"
    )
    assert result["html"] == (
        "<ul><li>First sentence. (Source A)</li>"
        "<li>Second <b>word</b> sentence. (Source B)</li></ul>"
    )
    assert result["changed"] is True


def test_dedupes_exact_sentence_already_present():
    existing = (
        "<ul><li>You've won again, you lucky <b>beggar</b>.</li>"
        "<li>They're eating you alive, the <b>beggars</b>. (The Guard 2011)</li></ul>"
    )
    result = build_example_html.build(
        existing,
        "beggars",
        "They're eating you alive, the beggars.",
        "The Guard 2011",
    )
    assert result == {"html": existing, "changed": False, "already_present": True}


def test_word_not_found_in_sentence_leaves_it_unbolded(capsys):
    result = build_example_html.build(None, "missing", "A sentence without that word.", None)
    assert "<b>" not in result["html"]
    assert "warning" in capsys.readouterr().err


def test_cli_end_to_end():
    payload = {
        "existing": "You've won again, you lucky <b>beggar</b>.",
        "word": "beggars",
        "sentence": "They're eating you alive, the beggars.",
        "source": "The Guard 2011",
    }
    result = subprocess.run(
        [sys.executable, str(SCRIPT)],
        input=json.dumps(payload),
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    parsed = json.loads(result.stdout)
    assert parsed["changed"] is True
    assert parsed["html"] == (
        "<ul><li>You've won again, you lucky <b>beggar</b>.</li>"
        "<li>They're eating you alive, the <b>beggars</b>. (The Guard 2011)</li></ul>"
    )

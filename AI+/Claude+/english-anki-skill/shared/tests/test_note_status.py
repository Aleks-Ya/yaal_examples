import json
import subprocess
import sys
from pathlib import Path

import note_status

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "note_status.py"


def complete_fields():
    """A note in which every Claude field (text + audio) is legitimately present."""
    return {
        "English": "a beggar",
        "Transcription": "ˈbɛɡər",
        "Definition": "a person who lives by asking others for money",
        "Picture": '<img src="beggar-noun.jpg">',
        "Russian": "нищий",
        "Synonym1": "pauper",
        "Synonyms": "<ul><li>pauper</li></ul>",
        "Antonym1": "millionaire",
        "Antonyms": "<ul><li>millionaire</li></ul>",
        "Examples1-generated": "<ul><li>The <b>beggar</b> sat by the road.</li></ul>",
        "English-audio-generated": "[sound:beggar-noun-english.mp3]",
        "Definition-audio-generated": "[sound:beggar-noun-definition.mp3]",
        "Synonym1-audio-generated": "[sound:beggar-noun-synonym1.mp3]",
        "Antonym1-audio-generated": "[sound:beggar-noun-antonym1.mp3]",
    }


def test_is_empty_variants():
    assert note_status.is_empty("")
    assert note_status.is_empty(None)
    assert note_status.is_empty("   ")
    assert note_status.is_empty("<br>")
    assert note_status.is_empty("&nbsp;")
    assert not note_status.is_empty("hello")
    assert not note_status.is_empty("<ul><li>hi</li></ul>")
    assert not note_status.is_empty('<img src="x.jpg">')  # Picture: tags-only but meaningful
    assert not note_status.is_empty("[sound:x.mp3]")       # audio: no tags but meaningful


def test_all_empty_new_note_lists_every_backfillable_field():
    status = note_status.compute_status({"English": "a beggar"}, [])
    assert status["empty_claude_fields"] == note_status.CLAUDE_TEXT_FIELDS
    assert status["audio_to_generate"] == ["English-audio-generated"]  # only English has a source
    assert status["complete"] is False


def test_fully_complete_note_is_complete():
    status = note_status.compute_status(complete_fields(), ["en::parts::noun::countable"])
    assert status["empty_claude_fields"] == []
    assert status["audio_to_generate"] == []
    assert status["complete"] is True
    assert status["incomplete_reasons"] == []


def test_absence_tagged_empty_fields_count_as_complete():
    fields = complete_fields()
    fields["Synonym1"] = fields["Synonyms"] = ""
    fields["Antonym1"] = fields["Antonyms"] = ""
    fields["Picture"] = ""
    fields["Synonym1-audio-generated"] = fields["Antonym1-audio-generated"] = ""
    tags = [
        "~api::absent::synonym1",
        "~api::absent::synonyms",
        "~api::absent::antonym1",
        "~api::absent::antonyms",
        "~api::absent::picture",
    ]
    status = note_status.compute_status(fields, tags)
    assert status["empty_claude_fields"] == []
    assert set(status["absent_ok_fields"]) == {"Synonym1", "Synonyms", "Antonym1", "Antonyms", "Picture"}
    assert status["audio_to_generate"] == []  # no Synonym1/Antonym1 source -> no audio expected
    assert status["complete"] is True


def test_untagged_empty_field_is_backfill_work_not_absence():
    fields = complete_fields()
    fields["Synonym1"] = ""
    status = note_status.compute_status(fields, [])  # no absence tag
    assert "Synonym1" in status["empty_claude_fields"]
    assert status["complete"] is False


def test_missing_required_audio_blocks_completeness():
    fields = complete_fields()
    fields["Definition-audio-generated"] = ""
    status = note_status.compute_status(fields, [])
    assert status["audio_to_generate"] == ["Definition-audio-generated"]
    assert status["complete"] is False


def test_synonym1_audio_not_required_when_synonym1_empty():
    fields = complete_fields()
    fields["Synonym1"] = ""
    fields["Synonym1-audio-generated"] = ""
    status = note_status.compute_status(fields, ["~api::absent::synonym1"])
    assert "Synonym1-audio-generated" not in status["audio_to_generate"]
    assert status["complete"] is True


def test_no_pictures_skips_empty_untagged_picture():
    fields = complete_fields()
    fields["Picture"] = ""
    plain = note_status.compute_status(fields, ["en::to-refine"])
    assert "Picture" in plain["empty_claude_fields"]
    assert plain["complete"] is False

    skipped = note_status.compute_status(fields, ["en::to-refine"], no_pictures=True)
    assert skipped["empty_claude_fields"] == []
    assert skipped["skipped_fields"] == ["Picture"]
    assert skipped["complete"] is True
    assert skipped["remove_refine_tag"] is True


def test_no_pictures_leaves_absence_tagged_picture_in_absent_ok():
    fields = complete_fields()
    fields["Picture"] = ""
    status = note_status.compute_status(fields, ["~api::absent::picture"], no_pictures=True)
    assert status["absent_ok_fields"] == ["Picture"]
    assert status["skipped_fields"] == []
    assert status["complete"] is True


def test_no_pictures_does_not_mask_other_missing_fields():
    fields = complete_fields()
    fields["Picture"] = ""
    fields["Russian"] = ""
    status = note_status.compute_status(fields, ["en::to-refine"], no_pictures=True)
    assert status["empty_claude_fields"] == ["Russian"]
    assert status["complete"] is False
    assert status["remove_refine_tag"] is False


def test_remove_refine_tag_gated_by_tag_presence():
    fields = complete_fields()
    assert note_status.compute_status(fields, [])["remove_refine_tag"] is False
    assert note_status.compute_status(fields, ["en::to-refine"])["remove_refine_tag"] is True


def test_cli_end_to_end():
    payload = {"fields": complete_fields(), "tags": ["en::to-refine"]}
    result = subprocess.run(
        [sys.executable, str(SCRIPT)],
        input=json.dumps(payload),
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    out = json.loads(result.stdout)
    assert out["complete"] is True
    assert out["remove_refine_tag"] is True


def test_cli_no_pictures_flag():
    fields = complete_fields()
    fields["Picture"] = ""
    payload = {"fields": fields, "tags": ["en::to-refine"]}
    result = subprocess.run(
        [sys.executable, str(SCRIPT), "--no-pictures"],
        input=json.dumps(payload),
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    out = json.loads(result.stdout)
    assert out["skipped_fields"] == ["Picture"]
    assert out["complete"] is True
    assert out["remove_refine_tag"] is True

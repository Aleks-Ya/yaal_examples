import json
import subprocess
import sys
from pathlib import Path

import slugify

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "slugify.py"


def test_strips_leading_article():
    assert slugify.slugify_word("a beggar") == "beggar"
    assert slugify.slugify_word("an idea") == "idea"
    assert slugify.slugify_word("to conquer") == "conquer"
    assert slugify.slugify_word("Beggar") == "beggar"


def test_does_not_strip_mid_word_article():
    # only a *leading* a/an/to prefix is cosmetic
    assert slugify.slugify_word("in a predictable way") == "in-a-predictable-way"


def test_collapses_non_alphanumerics_to_single_hyphen():
    assert slugify.slugify_word("to bat around") == "bat-around"
    assert slugify.slugify_word("well-known!!") == "well-known"


def test_build_slug_picture_has_no_field():
    assert slugify.build_slug("to bat around", "verb", ext="jpg") == {
        "slug": "bat-around-verb",
        "filename": "bat-around-verb.jpg",
    }


def test_build_slug_audio_field_and_ext():
    assert slugify.build_slug("a beggar", "noun", field="english", ext="mp3") == {
        "slug": "beggar-noun-english",
        "filename": "beggar-noun-english.mp3",
    }


def test_build_slug_without_ext_filename_equals_slug():
    assert slugify.build_slug("reluctant", "adjective") == {
        "slug": "reluctant-adjective",
        "filename": "reluctant-adjective",
    }


def test_build_all_media_lists_picture_and_all_audio():
    assert slugify.build_all_media("a beggar", "noun") == {
        "picture": "beggar-noun.jpg",
        "english": "beggar-noun-english.mp3",
        "definition": "beggar-noun-definition.mp3",
        "synonym1": "beggar-noun-synonym1.mp3",
        "antonym1": "beggar-noun-antonym1.mp3",
    }


def test_cli_end_to_end():
    result = subprocess.run(
        [sys.executable, str(SCRIPT), "a beggar", "noun", "--field", "english", "--ext", "mp3"],
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    assert json.loads(result.stdout) == {
        "slug": "beggar-noun-english",
        "filename": "beggar-noun-english.mp3",
    }


def test_cli_all_media():
    result = subprocess.run(
        [sys.executable, str(SCRIPT), "to bat around", "verb", "--all-media"],
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    parsed = json.loads(result.stdout)
    assert parsed["picture"] == "bat-around-verb.jpg"
    assert parsed["english"] == "bat-around-verb-english.mp3"
    assert set(parsed) == {"picture", "english", "definition", "synonym1", "antonym1"}


def test_cli_all_media_rejects_field_and_ext():
    result = subprocess.run(
        [sys.executable, str(SCRIPT), "a beggar", "noun", "--all-media", "--ext", "jpg"],
        capture_output=True,
        text=True,
    )
    assert result.returncode == 2
    assert "--all-media" in result.stderr

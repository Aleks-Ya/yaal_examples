import json
import subprocess
import sys
from pathlib import Path

import find_duplicate

SCRIPT = Path(__file__).resolve().parent.parent / "scripts" / "find_duplicate.py"


def test_normalize_word_strips_a_an_to_and_lowercases():
    assert find_duplicate.normalize_word("Beggar") == "beggar"
    assert find_duplicate.normalize_word("a bucket") == "bucket"
    assert find_duplicate.normalize_word("an idea") == "idea"
    assert find_duplicate.normalize_word("to conquer") == "conquer"
    assert find_duplicate.normalize_word("conquer") == "conquer"


def test_normalize_word_does_not_strip_mid_word_article():
    # "in a predictable way" must NOT become "predictable way" -- only a
    # leading "a "/"an "/"to " counts as the cosmetic prefix.
    assert find_duplicate.normalize_word("in a predictable way") == "in a predictable way"


def test_pos_family_match_exact():
    assert find_duplicate.pos_family_match("en::parts::noun", ["en::parts::noun"])


def test_pos_family_match_parent_tag_matches_child_target():
    assert find_duplicate.pos_family_match(
        "en::parts::noun::countable", ["en::parts::noun", "source::en::smeag::2020"]
    )


def test_pos_family_match_child_tag_matches_parent_target():
    assert find_duplicate.pos_family_match(
        "en::parts::noun", ["en::parts::noun::countable"]
    )


def test_pos_family_match_ignores_non_pos_tags():
    assert not find_duplicate.pos_family_match(
        "en::parts::adjective", ["en::unit::phrase", "source::movie::the-guard"]
    )


def test_pos_family_match_unrelated_pos_does_not_match():
    assert not find_duplicate.pos_family_match(
        "en::parts::verb", ["en::parts::noun"]
    )


def test_find_duplicates_beggar_matches_despite_tag_granularity_difference():
    duplicates = find_duplicate.find_duplicates(
        "beggar",
        "en::parts::noun::countable",
        [{"id": 1579307261208, "english": "beggar", "tags": ["en::parts::noun"]}],
    )
    assert duplicates == [1579307261208]


def test_find_duplicates_predictable_phrase_note_is_not_a_duplicate():
    duplicates = find_duplicate.find_duplicates(
        "predictable",
        "en::parts::adjective",
        [{"id": 1482172556889, "english": "in a predictable way", "tags": ["en::unit::phrase"]}],
    )
    assert duplicates == []


def test_find_duplicates_no_candidates_returns_empty_list():
    assert find_duplicate.find_duplicates("to bat around", "en::parts::verb::phrasal", []) == []


def test_find_duplicates_article_prefix_on_either_side_is_ignored():
    duplicates = find_duplicate.find_duplicates(
        "to conquer",
        "en::parts::verb",
        [{"id": 999, "english": "conquer", "tags": ["en::parts::verb"]}],
    )
    assert duplicates == [999]


def test_find_duplicates_multiple_matches_are_all_returned():
    duplicates = find_duplicate.find_duplicates(
        "beggar",
        "en::parts::noun",
        [
            {"id": 1, "english": "beggar", "tags": ["en::parts::noun"]},
            {"id": 2, "english": "a beggar", "tags": ["en::parts::noun::countable"]},
        ],
    )
    assert duplicates == [1, 2]


def test_cli_end_to_end():
    payload = {
        "word": "beggar",
        "pos_tag": "en::parts::noun::countable",
        "candidates": [{"id": 1579307261208, "english": "beggar", "tags": ["en::parts::noun"]}],
    }
    result = subprocess.run(
        [sys.executable, str(SCRIPT)],
        input=json.dumps(payload),
        capture_output=True,
        text=True,
    )
    assert result.returncode == 0
    assert json.loads(result.stdout) == {"duplicates": [1579307261208]}

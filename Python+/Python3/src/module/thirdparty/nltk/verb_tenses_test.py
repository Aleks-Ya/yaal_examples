import nltk
import pytest


@pytest.mark.skip(reason="Run once")
def test_download_data():
    nltk.download('wordnet')


@pytest.mark.skip(reason="Does not work")
def test_convert_verb_to_past():
    verb: str = "run"
    past_tense: str = ""
    assert past_tense == "ran"

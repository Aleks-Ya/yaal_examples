from japanese_verb_conjugator_v2 import VerbClass, generate_japanese_verb_by_str


def test_plain():
    word: str = generate_japanese_verb_by_str("飲む", VerbClass.GODAN, "pla")
    assert word == "飲む"


def test_plain_past_negative():
    word: str = generate_japanese_verb_by_str("飲む", VerbClass.GODAN, "pla", "past", "neg")
    assert word == '飲まなかった'


def test_passive_polite_negative():
    word: str = generate_japanese_verb_by_str("飲む", VerbClass.GODAN, "pass", "pol", "neg")
    assert word == '飲まれません'

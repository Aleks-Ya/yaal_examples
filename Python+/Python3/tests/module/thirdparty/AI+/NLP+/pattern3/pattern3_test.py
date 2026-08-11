from pattern3.text.en import conjugate, PAST


def test_verb_past_tense():
    verb: str = "run"
    past_tense: str = conjugate(verb, tense=PAST)
    print(past_tense)

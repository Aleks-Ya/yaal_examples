def test_concatenate_strings():
    a: str = "a" + 'b'
    assert a == 'ab'


def test_repeat_strings():
    a: str = 'ab' * 3
    assert a == 'ababab'


def test_get_character_by_index():
    text: str = 'abcd'
    fromBegin: str = text[1]
    fromEnd: str = text[-1]
    assert fromBegin == 'b'
    assert fromEnd == 'd'
    assert text[0] == text[-0]


def test_string_slicing():
    text: str = 'abcd'

    sub: str = text[1:3]
    assert sub == 'bc'

    sub = text[:3]
    assert sub == 'abc'

    sub = text[1:]
    assert sub == 'bcd'

    sub = text[-2:]
    assert sub == 'cd'

# Convert string to number


def test_convert_to_int():
    a: int = int('5')
    assert a == 5


def test_convert_to_bool():
    b: bool = bool('True')
    assert b

    b = bool('')
    assert not b


def test_to_bool():
    b: bool = __str_to_bool('True')
    assert b

    b = __str_to_bool('False')
    assert not b


def __str_to_bool(s: str):
    return s.lower() in ['true', '1', 't', 'y', 'yes']

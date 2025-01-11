def test_float_is_int():
    f: float = 123.4
    assert not f.is_integer()


def test_variable_contains_float():
    f: float = 1.1
    assert isinstance(f, float)

    i: int = 1
    assert not isinstance(i, float)

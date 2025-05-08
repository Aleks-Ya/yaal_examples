def test_literals():
    assert True
    assert not False


def test_boolean_variable():
    a: bool = True
    assert a


def test_is_variable_boolean():
    a: bool = True
    assert type(a) == bool
    b: str = 'True'
    assert type(b) != bool
    c: None = None
    assert type(c) != bool

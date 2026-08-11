# Class, no subclass
def test_exact_type():
    x = 5
    assert type(x) is int


# Class or subclass
def test_is_instance():
    x = 5
    assert isinstance(x, int)


def test_multiple_types():
    x = "hello"
    assert isinstance(x, (str, bytes))

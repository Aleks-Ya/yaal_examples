def test_exact_string():
    result: str = "hello"
    assert result == "hello"


def test_contains():
    msg: str = "File not found: config.yml"
    assert "not found" in msg
    assert "config.yml" in msg


def test_multiline():
    actual: str = "a\nb\n"
    expected: str = "a\nb\n"
    assert actual == expected


def test_multiline_stripped():
    actual: str = "a\nb\n"
    expected: str = "a\nb"
    assert actual.strip() == expected.strip()

from typing import Optional


def test_string_contains_substring():
    s: str = 'abc'
    assert 'b' in s
    assert 'z' not in s


def test_is_empty():
    s: str = ''
    is_empty: bool = not s
    assert is_empty


def test_is_not_empty():
    s: str = 'a'
    assert s


def test_is_not_None_and_not_empty():
    s: Optional[str] = None
    assert not s

    s = ''
    assert not s

    s = 'a'
    assert s

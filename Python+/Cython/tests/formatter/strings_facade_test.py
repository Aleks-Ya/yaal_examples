from src.formatter.strings_facade import get_string_formatted


def test_get_string_formatted():
    assert get_string_formatted("hello") == "HELLO"

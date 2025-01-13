from src.formatter_facade.formatter_facade import prepare_text, multiply_numbers


def test_prepare_text():
    assert prepare_text("hello") == "HELLO"


def test_multiply_numbers():
    assert multiply_numbers(2, 3) == 6

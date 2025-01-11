from src.formatter_facade.formatter_facade import prepare_text


def test_prepare_text():
    assert prepare_text("hello") == "HELLO"

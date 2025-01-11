from src.formatter.strings import upper_case


def test_upper_case():
    assert upper_case("hello") == "HELLO"
    assert upper_case("Hello") == "HELLO"
    assert upper_case("123") == "123"
    assert upper_case("hello world!") == "HELLO WORLD!"
    assert upper_case("") == ""

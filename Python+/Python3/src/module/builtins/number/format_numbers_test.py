def test_thousands_comma_split():
    i: float = 123456.3
    f: str = f"{i:,}"
    assert f == '123,456.3'


def test_thousands_space_split():
    i: float = 123456.3
    f: str = f"{i:,}".replace(',', ' ')
    assert f == '123 456.3'


def test_padding():
    i: int = 123
    f: str = f"a{i:10,}b"
    assert f == 'a       123b'


def test_padding_with_leading_zeros():
    i: int = 123
    f: str = f"a{i:010}b"
    assert f == 'a0000000123b'


def test_float_to_percent():
    i: float = 0.756
    p: int = round(i * 100)
    f: str = f"{p}%"
    assert f == '76%'


def test_number_after_dot_1():
    i: float = 123.123456
    f: str = f"a{i:0.2f}b"
    assert f == 'a123.12b'


def test_number_after_dot_2():
    i: float = 123.123456
    f: str = "a{:.2f}b".format(i)
    assert f == 'a123.12b'


def test_significant_digits_float():
    i: float = 123.123456
    f: str = "a{:.2g}b".format(i)
    assert f == 'a1.2e+02b'


def test_significant_digits_int():
    i: int = 123456
    f: str = "a{:.2g}b".format(i)
    assert f == 'a1.2e+05b'

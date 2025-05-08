import pytest


def test_sum():
    a: int = 1 + 2
    assert a == 3


def test_floor_division():
    b: int = 10 // 3
    assert b == 3


def test_modulo():
    c: int = 10 % 3
    assert c == 1


def test_power():
    d: int = 5 ** 2
    assert d == 25


def test_floating_point_operations():
    e: float = 5.2 * .3
    assert e == pytest.approx(1.56)


def test_number_of_digits_in_int():
    f: int = 12345
    n: int = len(str(f))
    assert n == 5

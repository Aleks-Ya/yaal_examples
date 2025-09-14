import math


def test_calculate_hypotenuse():
    a: int = 1
    b: int = 2
    c: float = math.hypot(a, b)
    assert c == 2.23606797749979


def test_int_hypotenuse():
    a: int = 1
    b: int = 2
    c: float = math.hypot(a, b)
    c2: int = int(c)
    assert c2 == 2

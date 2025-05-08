import math

import pytest


def test_instantiate_infinity():
    plus_infinity_1: float = float("inf")
    plus_infinity_2: float = float("+inf")
    plus_infinity_3: float = math.inf

    minus_infinity_1: float = float("-inf")
    minus_infinity_2: float = -math.inf

    assert math.isinf(plus_infinity_1)
    assert math.isinf(plus_infinity_2)
    assert math.isinf(plus_infinity_3)
    assert math.isinf(minus_infinity_1)
    assert plus_infinity_1 > minus_infinity_2
    assert plus_infinity_3 > minus_infinity_2

def test_int_infinity():
    with pytest.raises(OverflowError):
        float_infinity: float = float("inf")
        int(float_infinity)

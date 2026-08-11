import pytest


@pytest.mark.parametrize("a,b,total", [
    (1, 2, 3),
    (2, 3, 5),
    (3, 4, 7),
])
def test_addition(a, b, total):
    assert a + b == total

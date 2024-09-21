import pytest


def test_zero_division():
    with pytest.raises(ZeroDivisionError):
        # noinspection PyStatementEffect
        1 / 0

from pytest import raises


def test_zero_division():
    with raises(ZeroDivisionError):
        # noinspection PyStatementEffect
        1 / 0

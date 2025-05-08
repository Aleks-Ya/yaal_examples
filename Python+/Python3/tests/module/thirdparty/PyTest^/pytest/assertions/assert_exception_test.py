from pytest import raises


def test_zero_division():
    with raises(ZeroDivisionError):
        # noinspection PyStatementEffect
        1 / 0


def test_assert_message():
    with raises(ZeroDivisionError, match="division by zero"):
        # noinspection PyStatementEffect
        1 / 0

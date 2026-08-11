from pytest import raises


def test_zero_division():
    with raises(ZeroDivisionError):
        # noinspection PyStatementEffect
        1 / 0


def test_assert_message():
    with raises(ZeroDivisionError, match="division by zero"):
        # noinspection PyStatementEffect
        1 / 0


def test_exception_info():
    with raises(ZeroDivisionError) as ex_info:
        # noinspection PyStatementEffect
        1 / 0
    e: ZeroDivisionError = ex_info.value
    assert "division by zero" in e.args

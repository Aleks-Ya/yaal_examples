import traceback
from typing import List


def test_in_try_except():
    try:
        raise ZeroDivisionError('never divide by zero!')
    except ArithmeticError as e:
        text: str = traceback.format_exc()
        assert 'ZeroDivisionError' in text
        assert 'never divide by zero!' in text


def test_format_exception():
    e: Exception = ZeroDivisionError('never divide by zero!')
    format_exception_str: List[str] = traceback.format_exception(e)
    assert any('ZeroDivisionError' in line for line in format_exception_str)
    assert any('never divide by zero!' in line for line in format_exception_str)


def test_stacktrace_from_exception():
    e: Exception = ZeroDivisionError('never divide by zero!')
    exc_type, exc_value, exc_traceback = type(e), e, e.__traceback__
    stack_trace = ''.join(traceback.format_exception(exc_type, exc_value, exc_traceback))
    assert 'ZeroDivisionError' in stack_trace
    assert 'never divide by zero!' in stack_trace

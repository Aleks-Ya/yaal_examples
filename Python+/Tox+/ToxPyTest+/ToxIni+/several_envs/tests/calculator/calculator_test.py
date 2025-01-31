import pytest

from application.calculator.calculator import summarize


@pytest.fixture
def addendum() -> int:
    return 3


def test_summarize(addendum: int):
    assert summarize(2, addendum) == 5

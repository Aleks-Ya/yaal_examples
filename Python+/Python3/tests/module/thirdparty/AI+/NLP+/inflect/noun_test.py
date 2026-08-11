import pytest
from inflect import engine


@pytest.fixture
def p() -> engine:
    return engine()


def test_plural_noun(p: engine):
    plural: str = p.plural("cat")
    assert plural == "cats"


def test_plural_noun_spanish(p: engine):
    assert p.plural("nariz") == "narizzes"  # should be "narices"

import pytest
from inflect import engine


@pytest.fixture
def p() -> engine:
    return engine()


def test_plural_noun(p: engine):
    plural: str = p.plural("cat")
    assert plural == "cats"


def test_verb_ing(p: engine):
    ing: str = p.present_participle("run")
    assert ing == "running"
    assert p.present_participle("lie") == "lying"

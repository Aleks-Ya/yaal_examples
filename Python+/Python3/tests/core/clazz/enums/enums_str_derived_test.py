from enum import Enum

import pytest


class Color(Enum):
    RED = "red"
    GREEN = "green"
    BLUE = "blue"


def test_enum():
    assert str(Color.RED) == 'Color.RED'
    assert Color.RED.name == 'RED'
    assert Color.RED.value == 'red'
    assert Color['RED'] == Color.RED
    assert Color('red') == Color.RED

    member: Color = Color.RED
    assert member.name == 'RED'
    assert member.value == 'red'


def test_get_by_name():
    assert Color['RED'] == Color.RED
    with pytest.raises(KeyError):
        # noinspection PyStatementEffect
        Color['YELLOW']
    with pytest.raises(KeyError):
        # noinspection PyStatementEffect,PyTypeChecker
        Color[None]
    with pytest.raises(KeyError):
        # noinspection PyStatementEffect
        Color['']


def test_iterate():
    act_values: list[str] = [str(color) for color in Color]
    assert act_values == ["Color.RED", "Color.GREEN", "Color.BLUE"]
    assert list(Color) == [Color.RED, Color.GREEN, Color.BLUE]
    assert set(Color) == {Color.RED, Color.GREEN, Color.BLUE}

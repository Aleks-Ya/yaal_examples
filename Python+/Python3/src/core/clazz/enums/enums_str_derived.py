from enum import Enum


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


def test_enum_iteration():
    expected_values: list[str] = ["Color.RED", "Color.GREEN", "Color.BLUE"]
    actual_values: list[str] = [str(color) for color in Color]
    assert actual_values == expected_values

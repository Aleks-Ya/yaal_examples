from enum import Enum


class Color(int, Enum):
    RED = 1
    GREEN = 2
    BLUE = 3


def test_enum():
    assert str(Color.RED) == 'Color.RED'
    assert Color.RED.name == 'RED'
    assert Color.RED.value == 1
    assert Color['RED'] == Color.RED
    assert Color(1) == Color.RED

    member = Color.RED
    assert member.name == 'RED'
    assert member.value == 1


def test_get_by_name():
    assert Color['RED'] == Color.RED


def test_enum_iteration():
    expected_values = ["Color.RED", "Color.GREEN", "Color.BLUE"]
    actual_values = [str(color) for color in Color]
    assert actual_values == expected_values

from enum import Enum


class Color(Enum):
    RED = "red"
    GREEN = "green"
    BLUE = "blue"


class Gender(Enum):
    MALE = "male"
    FEMALE = "female"

    def __repr__(self) -> str:
        return self.value

    def __str__(self) -> str:
        return self.name.capitalize()


def test_default_to_string():
    assert str(Color.RED) == 'Color.RED'
    assert str([Color.RED, Color.GREEN]) == "[<Color.RED: 'red'>, <Color.GREEN: 'green'>]"


def test_custom_str():
    assert str(Gender.FEMALE) == 'Female'
    assert str([Gender.MALE, Gender.FEMALE]) == "[male, female]"

from enum import Enum


class Color(Enum):
    RED = 1
    GREEN = 2
    BLUE = 3


assert str(Color.RED) == 'Color.RED'

assert Color.RED.name == 'RED'
assert Color.RED.value == 1

assert Color['RED'] == Color.RED
assert Color(1) == Color.RED

member = Color.RED
assert member.name == 'RED'
assert member.value == 1

for color in Color:
    print(color)

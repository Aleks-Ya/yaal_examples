import unittest
from enum import Enum


class Color(Enum):
    RED = "red"
    GREEN = "green"
    BLUE = "blue"


class TestStrDerivedEnum(unittest.TestCase):

    def test_enum(self):
        self.assertEqual(str(Color.RED), 'Color.RED')
        self.assertEqual(Color.RED.name, 'RED')
        self.assertEqual(Color.RED.value, 'red')
        self.assertEqual(Color['RED'], Color.RED)
        self.assertEqual(Color('red'), Color.RED)

        member = Color.RED
        self.assertEqual(member.name, 'RED')
        self.assertEqual(member.value, 'red')

    def test_enum_iteration(self):
        expected_values = ["Color.RED", "Color.GREEN", "Color.BLUE"]
        actual_values = [str(color) for color in Color]
        self.assertListEqual(actual_values, expected_values)


if __name__ == "__main__":
    unittest.main()

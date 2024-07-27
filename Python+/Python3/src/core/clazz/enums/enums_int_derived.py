import unittest
from enum import Enum


class Color(int, Enum):
    RED = 1
    GREEN = 2
    BLUE = 3


class TestIntDerivedEnum(unittest.TestCase):

    def test_enum(self):
        self.assertEqual(str(Color.RED), 'Color.RED')
        self.assertEqual(Color.RED.name, 'RED')
        self.assertEqual(Color.RED.value, 1)
        self.assertEqual(Color['RED'], Color.RED)
        self.assertEqual(Color(1), Color.RED)

        member = Color.RED
        self.assertEqual(member.name, 'RED')
        self.assertEqual(member.value, 1)

    def test_enum_iteration(self):
        expected_values = ["Color.RED", "Color.GREEN", "Color.BLUE"]
        actual_values = [str(color) for color in Color]
        self.assertListEqual(actual_values, expected_values)


if __name__ == "__main__":
    unittest.main()

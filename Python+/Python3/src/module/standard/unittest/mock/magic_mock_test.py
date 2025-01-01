import unittest
from unittest.mock import MagicMock


class FieldClass:
    def __init__(self):
        self.person: str = "John"


class FunctionClass:
    def my_function(self) -> str:
        return "abc"

    def upper_case(self, text: str) -> str:
        return text.upper()


class TestMagicMock(unittest.TestCase):
    def test_mock_field(self):
        thing: FieldClass = MagicMock()
        thing.person = "Mary"
        self.assertEqual(thing.person, "Mary")

    def test_mock_function_without_arguments(self):
        thing: FunctionClass = FunctionClass()
        self.assertEqual(thing.my_function(), "abc")
        thing.my_function = MagicMock(return_value="xyz")
        res: str = thing.my_function()
        self.assertEqual(res, "xyz")
        thing.my_function.assert_called_once()
        thing.my_function.assert_called_once_with()

    def test_mock_function_with_arguments(self):
        thing: FunctionClass = FunctionClass()
        self.assertEqual(thing.upper_case("abc"), "ABC")
        thing.upper_case = MagicMock(return_value="xyz")
        self.assertEqual(thing.upper_case("abc"), "xyz")
        thing.upper_case.assert_called_once_with("abc")

    def test_mock_class(self):
        thing: FunctionClass = MagicMock()
        self.assertIsInstance(thing, MagicMock)
        self.assertNotIsInstance(thing, FunctionClass)
        self.assertIsInstance(thing.my_function(), MagicMock)
        thing.my_function = MagicMock(return_value="xyz")
        res: str = thing.my_function()
        self.assertEqual(res, "xyz")
        thing.my_function.assert_called_once()
        thing.my_function.assert_called_once_with()


if __name__ == '__main__':
    unittest.main()

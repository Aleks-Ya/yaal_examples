import unittest
from unittest.mock import Mock


class FieldClass:
    def __init__(self):
        self.person: str = "John"


class FunctionClass:
    def my_function(self) -> str:
        return "abc"

    def upper_case(self, text: str) -> str:
        return text.upper()


class TestMock(unittest.TestCase):
    def test_mock_field(self):
        thing: FieldClass = Mock()
        thing.person = "Mary"
        self.assertEqual(thing.person, "Mary")

    def test_mock_function_without_arguments(self):
        thing: FunctionClass = FunctionClass()
        self.assertEqual(thing.my_function(), "abc")
        thing.my_function = Mock(return_value="xyz")
        res: str = thing.my_function()
        self.assertEqual(res, "xyz")
        thing.my_function.assert_called_once()
        thing.my_function.assert_called_once_with()

    def test_mock_function_with_arguments(self):
        thing: FunctionClass = FunctionClass()
        self.assertEqual(thing.upper_case("abc"), "ABC")
        thing.upper_case = Mock(return_value="xyz")
        self.assertEqual(thing.upper_case("abc"), "xyz")
        thing.upper_case.assert_called_once_with("abc")

    def test_mock_class(self):
        thing: FunctionClass = Mock()
        self.assertIsInstance(thing, Mock)
        self.assertIsInstance(thing.my_function(), Mock)
        thing.my_function = Mock(return_value="xyz")
        res: str = thing.my_function()
        self.assertEqual(res, "xyz")
        thing.my_function.assert_called_once()
        thing.my_function.assert_called_once_with()


if __name__ == '__main__':
    unittest.main()

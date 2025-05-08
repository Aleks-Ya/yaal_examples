import unittest
from unittest.mock import MagicMock


class FieldClass:
    def __init__(self):
        self.person: str = "John"


class NestedClass:
    def nested_function(self) -> str:
        return "nested"


class FunctionClass:
    def __init__(self):
        self.nested_field: NestedClass = NestedClass()

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
        exp_result: str = "xyz"
        thing.my_function = MagicMock(return_value=exp_result)
        act_result: str = thing.my_function()
        self.assertEqual(act_result, exp_result)
        thing.my_function.assert_called_once()
        thing.my_function.assert_called_once_with()

    def test_mock_function_with_arguments(self):
        thing: FunctionClass = FunctionClass()
        self.assertEqual(thing.upper_case("abc"), "ABC")
        exp_result: str = "xyz"
        thing.upper_case = MagicMock(return_value=exp_result)
        self.assertEqual(thing.upper_case("abc"), exp_result)
        thing.upper_case.assert_called_once_with("abc")

    def test_mock_nested_function(self):
        thing: FunctionClass = FunctionClass()
        self.assertEqual(thing.nested_field.nested_function(), "nested")
        exp_result: str = "xyz"
        thing.nested_field.nested_function = MagicMock(return_value=exp_result)
        act_res: str = thing.nested_field.nested_function()
        self.assertEqual(act_res, exp_result)
        thing.nested_field.nested_function.assert_called_once()
        thing.nested_field.nested_function.assert_called_once_with()

    def test_mock_replace_nested_function(self):
        thing: FunctionClass = FunctionClass()
        self.assertEqual(thing.nested_field.nested_function(), "nested")

        exp_result_1: str = "xyz"
        thing.nested_field.nested_function = MagicMock(return_value=exp_result_1)
        self.assertEqual(thing.nested_field.nested_function(), exp_result_1)

        exp_result_2: str = "abc"
        thing.nested_field.nested_function = MagicMock(return_value=exp_result_2)
        self.assertEqual(thing.nested_field.nested_function(), exp_result_2)

    def test_mock_class(self):
        thing: FunctionClass = MagicMock()
        self.assertIsInstance(thing, MagicMock)
        self.assertNotIsInstance(thing, FunctionClass)
        self.assertIsInstance(thing.my_function(), MagicMock)
        exp_result: str = "xyz"
        thing.my_function = MagicMock(return_value=exp_result)
        act_res: str = thing.my_function()
        self.assertEqual(act_res, exp_result)
        thing.my_function.assert_called_once()
        thing.my_function.assert_called_once_with()


if __name__ == '__main__':
    unittest.main()

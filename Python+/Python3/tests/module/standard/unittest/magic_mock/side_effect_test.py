import unittest
from unittest.mock import MagicMock


class FunctionClass:
    def my_without_args(self) -> str:
        return "abc"

    def my_with_args(self, text: str) -> str:
        return text.upper()


class TestSideEffect(unittest.TestCase):

    def test_function_without_arguments_returns_different_values(self):
        # Default behavior
        thing: FunctionClass = FunctionClass()
        self.assertEqual(thing.my_without_args(), "abc")
        # Mocked behavior
        thing.my_without_args = MagicMock(side_effect=["xyz", "prs"])
        result1: str = thing.my_without_args()
        result2: str = thing.my_without_args()
        with self.assertRaises(StopIteration):
            thing.my_without_args()
        self.assertEqual(result1, "xyz")
        self.assertEqual(result2, "prs")
        thing.my_without_args.assert_called()

    def test_function_with_arguments_returns_different_values(self):
        # Default behavior
        thing: FunctionClass = FunctionClass()
        self.assertEqual(thing.my_with_args("abc"), "ABC")
        # Mocked behavior
        thing.my_with_args = MagicMock(side_effect=["xyz", "prs"])
        result1: str = thing.my_with_args("call1")
        result2: str = thing.my_with_args("call2")
        with self.assertRaises(StopIteration):
            thing.my_with_args("call3")
        self.assertEqual(result1, "xyz")
        self.assertEqual(result2, "prs")
        thing.my_with_args.assert_called()


if __name__ == '__main__':
    unittest.main()

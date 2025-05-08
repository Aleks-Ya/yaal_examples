import unittest
from unittest.mock import Mock


class FunctionClass:
    def my_without_args(self) -> str:
        return "abc"

    def my_with_args(self, text: str) -> str:
        return text.upper()


class TestAssertMockCalls(unittest.TestCase):

    def test_assert_called_once(self):
        thing: FunctionClass = FunctionClass()
        thing.my_without_args = Mock(return_value="xyz")
        thing.my_without_args.assert_not_called()
        self.assertEqual(thing.my_without_args(), "xyz")
        thing.my_without_args.assert_called_once()
        self.assertEqual(thing.my_without_args(), "xyz")
        thing.my_without_args.assert_any_call()

    def test_assert_any_call(self):
        thing: FunctionClass = FunctionClass()
        thing.my_with_args = Mock(return_value="xyz")
        thing.my_with_args.assert_not_called()
        self.assertEqual(thing.my_with_args("call1"), "xyz")
        thing.my_with_args.assert_called_once()
        thing.my_with_args.assert_any_call("call1")
        self.assertEqual(thing.my_with_args("call2"), "xyz")
        thing.my_with_args.assert_any_call("call2")


if __name__ == '__main__':
    unittest.main()

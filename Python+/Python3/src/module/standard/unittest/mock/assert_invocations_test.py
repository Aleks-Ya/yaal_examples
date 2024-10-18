import unittest
from unittest.mock import Mock


class FunctionClass:
    def my_function(self) -> str:
        return "abc"


class TestInvocations(unittest.TestCase):

    def test_assert_called_once(self):
        thing: FunctionClass = FunctionClass()
        thing.my_function = Mock(return_value="xyz")
        thing.my_function.assert_not_called()
        self.assertEqual(thing.my_function(), "xyz")
        thing.my_function.assert_called_once()
        self.assertEqual(thing.my_function(), "xyz")
        thing.my_function.assert_any_call()


if __name__ == '__main__':
    unittest.main()

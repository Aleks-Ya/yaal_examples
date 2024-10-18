import unittest
from unittest.mock import Mock


class FieldClass:
    def __init__(self):
        self.person: str = "John"


class FunctionClass:
    def my_function(self) -> str:
        return "abc"


class TestMock(unittest.TestCase):
    def test_mock_field(self):
        thing: FieldClass = Mock()
        thing.person = "Mary"
        self.assertEqual(thing.person, "Mary")

    def test_mock_function(self):
        thing: FunctionClass = FunctionClass()
        thing.my_function = Mock(return_value="xyz")
        res: str = thing.my_function()
        assert res == "xyz"


if __name__ == '__main__':
    unittest.main()

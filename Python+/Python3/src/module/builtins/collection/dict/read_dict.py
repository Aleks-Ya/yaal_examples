import unittest
from typing import Any


class TestReadDict(unittest.TestCase):
    def test_get_existing_key(self):
        d: dict[str, int] = dict(one=1, two=2)
        self.assertEqual(d['one'], 1)
        self.assertEqual(d.get('one'), 1)

    def test_get_absent_key(self):
        d: dict[Any, Any] = dict()
        self.assertIsNone(d.get('absent'))
        with self.assertRaises(KeyError) as ex:
            v = d['absent']
        self.assertEqual(ex.exception.args[0], "absent")

    def test_contains_key(self):
        d: dict[str, int] = dict(one=1, two=2)
        self.assertIn('one', d)
        self.assertNotIn('three', d)

    def test_size_key(self):
        d: dict[str, int] = dict(one=1, two=2)
        self.assertEqual(len(d), 2)

    def test_nested_dict(self):
        d: dict[str, dict[str, str]] = dict(address=dict(city="London"))
        self.assertEqual(d['address']['city'], "London")


if __name__ == '__main__':
    unittest.main()

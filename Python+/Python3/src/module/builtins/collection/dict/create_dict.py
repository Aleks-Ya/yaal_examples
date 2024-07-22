import unittest
from typing import Any, Dict


class TestCreateDict(unittest.TestCase):

    def test_empty_dict(self):
        empty: dict[Any, Any] = {}
        self.assertEqual(len(empty), 0)

    def test_dict_creation(self):
        a: dict[str, int] = dict(one=1, two=2, three=3)
        b: dict[str, int] = {'one': 1, 'two': 2, 'three': 3}
        c: dict[Any, Any] = dict(zip(['one', 'two', 'three'], [1, 2, 3]))
        d: dict[str, int] = dict([('two', 2), ('one', 1), ('three', 3)])
        e: dict[str, int] = dict({'three': 3, 'one': 1, 'two': 2})
        self.assertEqual(a, b)
        self.assertEqual(b, c)
        self.assertEqual(c, d)
        self.assertEqual(d, e)

    def test_dict_from_list_of_tuples(self):
        strings: list[str] = ['a', 'bb', 'ccc']
        d: dict[str, int] = dict([(s, len(s)) for s in strings])
        self.assertEqual(d, {'a': 1, 'bb': 2, 'ccc': 3})


if __name__ == '__main__':
    unittest.main()

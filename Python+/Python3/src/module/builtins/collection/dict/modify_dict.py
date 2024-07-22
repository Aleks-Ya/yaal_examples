import unittest
from typing import Dict, Any


class TestModifyDict(unittest.TestCase):
    def test_add_item_to_empty_dict(self):
        empty_dict: dict[str, int] = dict()
        empty_dict['one'] = 1
        empty_dict['two'] = 2
        self.assertEqual(empty_dict, dict(one=1, two=2))

    def test_add_item_to_exists_dict(self):
        n: dict[str, int] = dict(one=1, two=2)
        n['three'] = 3
        self.assertEqual(n, dict(one=1, two=2, three=3))

    def test_join_dictionaries(self):
        a: dict[str, int] = dict(one=1, two=2)
        b: dict[str, int] = dict(one=11, three=3)
        join: dict[str, int] = a.copy()
        join.update(b)
        self.assertEqual(join, dict(one=11, two=2, three=3))

    def test_remove_element_from_dict(self):
        d: dict[str, int] = {'a': 1, 'b': 2}
        del d['a']
        self.assertEqual(d, {'b': 2})

    def test_filter_dict(self):
        e: dict[str, int] = {'a': 1, 'b': -2, 'c': 3}
        filtered: dict[str, int] = {k: v for k, v in e.items() if v > 0}
        self.assertEqual(filtered, {'a': 1, 'c': 3})

    def test_filter_dict_using_lambda(self):
        e: dict[str, int] = {'a': 1, 'b': -2, 'c': 3}
        filtered: dict[Any, Any] = dict(filter(lambda val: val[1] > 0, e.items()))
        self.assertEqual(filtered, {'a': 1, 'c': 3})


if __name__ == '__main__':
    unittest.main()

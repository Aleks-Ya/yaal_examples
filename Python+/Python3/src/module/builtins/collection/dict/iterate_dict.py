import unittest
from typing import Dict


class TestIterateDict(unittest.TestCase):
    def test_iterate_keys(self):
        d: dict[str, int] = {'a': 1, 'b': 2, 'c': 3}
        res: str = ''
        for key in d:
            value: int = d[key]
            res += key
            res += str(value)
        self.assertEqual(res, 'a1b2c3')

    def test_iterate_values(self):
        d: dict[str, int] = {'a': 1, 'b': 2, 'c': 3}
        res: str = ''
        for value in d.values():
            res += str(value)
        self.assertEqual(res, '123')

    def test_iterate_values_one_line(self):
        d: dict[str, int] = {'a': 1, 'b': 2, 'c': 3}
        res: dict[int, str] = {value: 'a' for value in d.values()}
        self.assertDictEqual(res, {1: 'a', 2: 'a', 3: 'a'})

    def test_iterate_keys_values(self):
        d: dict[str, int] = {'a': 1, 'b': 2, 'c': 3}
        res: str = ''
        for key, value in d.items():
            res += key
            res += str(value)
        self.assertEqual(res, 'a1b2c3')


if __name__ == '__main__':
    unittest.main()

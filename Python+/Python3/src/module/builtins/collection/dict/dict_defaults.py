import unittest
from collections import defaultdict
from typing import Any


class TestDictDefaults(unittest.TestCase):

    def test_default_dict(self):
        price_dict: dict[Any, int] = defaultdict(lambda: 42)
        price_dict['car'] = 1000
        price_dict['house'] = 5000
        self.assertEqual(1000, price_dict['car'])
        self.assertEqual(5000, price_dict['house'])
        self.assertEqual(42, price_dict['airplane'])

    def test_dict_as_default_values(self):
        default_dict: dict[str, int] = {'car': 1000, 'house': 5000}
        actual_dict: dict[str, int] = {'car': 3000, 'airplane': 8000}
        backed_dict: dict[str, int] = default_dict.copy()
        backed_dict.update(actual_dict)
        self.assertEqual(3000, backed_dict['car'])
        self.assertEqual(5000, backed_dict['house'])
        self.assertEqual(8000, backed_dict['airplane'])

    def test_get_or_default(self):
        d: dict[str, int] = {'car': 1000}
        self.assertEqual(1000, d.get('car', 42))
        self.assertEqual(42, d.get('airplane', 42))

    def test_chain_of_dicts(self):
        absent: int = 42
        d1: dict[str, int] = {'car': 1000, 'house': 5000, 'airplane': 10000}
        d2: dict[str, int] = {'car': 2000, 'house': 6000}
        d3: dict[str, int] = {'car': 3000}
        self.assertEqual(3000, d3.get('car') or d2.get('car'))
        self.assertEqual(6000, d3.get('house') or d2.get('house'))
        self.assertEqual(10000, d3.get('airplane') or d2.get('airplane') or d1.get('airplane'))
        self.assertEqual(42, d3.get('absent_key') or d2.get('absent_key') or d1.get('absent_key') or absent)


if __name__ == "__main__":
    unittest.main()

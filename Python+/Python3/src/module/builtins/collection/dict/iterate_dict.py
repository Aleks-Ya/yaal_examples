import unittest


class TestIterateDict(unittest.TestCase):
    def test_iterate_keys(self):
        d = {'a': 1, 'b': 2, 'c': 3}
        res = ''
        for key in d:
            value = d[key]
            res += key
            res += str(value)
        self.assertEqual(res, 'a1b2c3')

    def test_iterate_values(self):
        d = {'a': 1, 'b': 2, 'c': 3}
        res = ''
        for value in d.values():
            res += str(value)
        self.assertEqual(res, '123')

    def test_iterate_values_one_line(self):
        d = {'a': 1, 'b': 2, 'c': 3}
        res = {value: 'a' for value in d.values()}
        self.assertDictEqual(res, {1: 'a', 2: 'a', 3: 'a'})

    def test_iterate_keys_values(self):
        d = {'a': 1, 'b': 2, 'c': 3}
        res = ''
        for key, value in d.items():
            res += key
            res += str(value)
        self.assertEqual(res, 'a1b2c3')


if __name__ == '__main__':
    unittest.main()

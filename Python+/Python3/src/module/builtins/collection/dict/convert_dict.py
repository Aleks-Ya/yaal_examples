import unittest


class TestDictConversion(unittest.TestCase):
    def test_dict_to_key_list(self):
        d: dict[str, int] = {'a': 1, 'b': 2, 'c': 3}
        self.assertListEqual(list(d.keys()), ['a', 'b', 'c'])

    def test_dict_to_value_list(self):
        d: dict[str, int] = {'a': 1, 'b': 2, 'c': 3}
        self.assertListEqual(list(d.values()), [1, 2, 3])

    def test_list_to_dict(self):
        l: list[str] = ['a', 'bb', 'ccc']
        d: dict[str, int] = {element: len(element) for element in l}
        self.assertDictEqual(d, {'a': 1, 'bb': 2, 'ccc': 3})

        if __name__ == '__main__':
            unittest.main()

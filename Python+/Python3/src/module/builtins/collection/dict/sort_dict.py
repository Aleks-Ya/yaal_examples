import unittest


class SortDictTestCase(unittest.TestCase):

    def test_sort_by_keys(self):
        origin_dict: dict[str, int] = {'two': 2, 'one': 1, 'three': 3}
        self.assertEqual(str(origin_dict), "{'two': 2, 'one': 1, 'three': 3}")

        sorted_asc_dict: dict[str, int] = dict(sorted(origin_dict.items()))
        self.assertEqual(str(sorted_asc_dict), "{'one': 1, 'three': 3, 'two': 2}")

        sorted_desc_dict: dict[str, int] = dict(sorted(origin_dict.items(), reverse=True))
        self.assertEqual(str(sorted_desc_dict), "{'two': 2, 'three': 3, 'one': 1}")

    def test_sort_by_values(self):
        origin_dict: dict[str, int] = {'two': 2, 'three': 3, 'one': 1}
        self.assertEqual(str(origin_dict), "{'two': 2, 'three': 3, 'one': 1}")

        sorted_asc_dict: dict[str, int] = dict(sorted(origin_dict.items(), key=lambda item: item[1]))
        self.assertEqual(str(sorted_asc_dict), "{'one': 1, 'two': 2, 'three': 3}")

        sorted_desc_dict: dict[str, int] = dict(sorted(origin_dict.items(), key=lambda item: item[1], reverse=True))
        self.assertEqual(str(sorted_desc_dict), "{'three': 3, 'two': 2, 'one': 1}")


if __name__ == '__main__':
    unittest.main()

import unittest
from typing import Any


class TestListOperations(unittest.TestCase):
    def test_empty_list(self):
        e: list[Any] = []
        self.assertEqual(len(e), 0)

    def test_single_element_list(self):
        single_element_list: list[int] = [25]
        self.assertEqual(single_element_list[0], 25)

    def test_contains_element(self):
        symbols: list[str] = ['a', 'b', 'c']
        self.assertTrue('b' in symbols)
        self.assertFalse('z' in symbols)

    def test_get_element_by_index(self):
        squares: list[int] = [1, 4, 9, 16, 25]
        self.assertEqual(squares[1], 4)
        self.assertEqual(squares[-2], 16)

    def test_copy(self):
        squares: list[int] = [1, 4, 9, 16, 25]
        copy: list[int] = squares[:]
        self.assertEqual(copy, squares)

    def test_replace(self):
        lst: list[int] = [1, 3, 3]
        lst[1] = 2
        self.assertEqual(lst, [1, 2, 3])

    def test_nested_lists(self):
        lst: list[list[int] | list[str]] = [[1, 2], ['a', 'b']]
        self.assertEqual(len(lst), 2)

    def test_is_empty(self):
        lst: list[Any] = []
        self.assertTrue(not lst)

    def test_is_not_empty(self):
        lst: list[int] = [1]
        self.assertTrue(lst)

    def test_iterate_list(self):
        lst: list[str] = ['a', 'b', 'c']
        result: str = ''
        for char in lst:
            result = result + char
        self.assertEqual(result, 'abc')

    def test_iterate_list_with_indexes(self):
        lst: list[str] = ['a', 'b', 'c']
        result: str = ''
        for i, char in enumerate(lst):
            result = result + str(i) + char
        self.assertEqual(result, '0a1b2c')

    def test_find_element_index(self):
        lst: list[str] = ['a', 'b', 'c']
        self.assertEqual(lst.index('b'), 1)

    def test_transform_list_elements(self):
        lst: list[str] = ['a', 'b', 'c']
        result_map = map(lambda symbol: symbol.upper(), lst)
        result_list = list(result_map)
        self.assertEqual(result_list, ['A', 'B', 'C'])

    def test_flat_map(self):
        list_of_lists: list[list[str]] = [['a', 'b'], ['c', 'd'], ['e']]
        flattened_list: list[str] = [item for sublist in list_of_lists for item in sublist]
        self.assertEqual(flattened_list, ['a', 'b', 'c', 'd', 'e'])


if __name__ == '__main__':
    unittest.main()

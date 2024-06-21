# "list comprehension" is iterating a list in braces "[]"

import unittest


class TestListComprehension(unittest.TestCase):

    def test_list_change(self):
        numbers: list[int] = [1, 2, 3]
        list_changed: list[int] = [num * 2 for num in numbers]
        self.assertEqual(list_changed, [2, 4, 6])

    def test_list_filter(self):
        numbers: list[int] = [1, 2, 3]
        list_filtered: list[int] = [num for num in numbers if num > 1]
        self.assertEqual(list_filtered, [2, 3])


if __name__ == "__main__":
    unittest.main()

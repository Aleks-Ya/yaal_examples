import unittest
from typing import List


class TestListDifference(unittest.TestCase):
    def test_difference(self):
        list1: List[str] = ['a', 'b', 'c']
        list2: List[str] = ['x', 'a', 'z', 'c']
        exp_list: List[str] = ['b', 'x', 'z']

        difference1: List[str] = list(set(list1) - set(list2)) + list(set(list2) - set(list1))
        difference1.sort()
        self.assertEqual(difference1, exp_list)

        difference2: List[str] = [i for i in list1 + list2 if i not in list1 or i not in list2]
        difference2.sort()
        self.assertEqual(difference2, exp_list)


if __name__ == '__main__':
    unittest.main()

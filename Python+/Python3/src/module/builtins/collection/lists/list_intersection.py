import unittest
from typing import List


class TestListIntersection(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.list1: List[str] = ['a', 'b', 'c']
        cls.list2: List[str] = ['x', 'a', 'z', 'c']
        cls.exp_list: List[str] = ['a', 'c']

    def test_intersection1(self):
        intersection1: List[str] = list(set(self.list1).intersection(set(self.list2)))
        self.assertListEqual(intersection1, self.exp_list)

    def test_intersection2(self):
        intersection2: List[str] = list(set(self.list1) & set(self.list2))
        self.assertListEqual(intersection2, self.exp_list)

    def test_intersection3(self):
        intersection3: List[str] = [item for item in self.list1 if item in self.list2]
        self.assertListEqual(intersection3, self.exp_list)


if __name__ == "__main__":
    unittest.main()

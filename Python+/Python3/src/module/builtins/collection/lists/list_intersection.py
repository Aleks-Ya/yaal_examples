from typing import List

list1: List[str] = ['a', 'b', 'c']
list2: List[str] = ['x', 'a', 'z', 'c']
exp_list: List[str] = ['a', 'c']


def test_intersection1():
    intersection1: List[str] = list(set(list1).intersection(set(list2)))
    assert intersection1 == exp_list


def test_intersection2():
    intersection2: List[str] = list(set(list1) & set(list2))
    assert intersection2 == exp_list


def test_intersection3():
    intersection3: List[str] = [item for item in list1 if item in list2]
    assert intersection3 == exp_list

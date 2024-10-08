from typing import List


def test_difference():
    list1: List[str] = ['a', 'b', 'c']
    list2: List[str] = ['x', 'a', 'z', 'c']
    exp_list: List[str] = ['b', 'x', 'z']

    difference1: List[str] = list(set(list1) - set(list2)) + list(set(list2) - set(list1))
    difference1.sort()
    assert difference1 == exp_list

    difference2: List[str] = [i for i in list1 + list2 if i not in list1 or i not in list2]
    difference2.sort()
    assert difference2 == exp_list

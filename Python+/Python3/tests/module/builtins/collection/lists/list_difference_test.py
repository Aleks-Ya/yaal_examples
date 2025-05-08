list1: list[str] = ['a', 'b', 'c']
list2: list[str] = ['x', 'a', 'z', 'c']
exp_list: list[str] = ['b', 'x', 'z']


def test_difference_1():
    difference: list[str] = list(set(list1) ^ set(list2))
    difference.sort()
    assert difference == exp_list


def test_difference_2():
    difference: list[str] = [i for i in list1 + list2 if i not in list1 or i not in list2]
    difference.sort()
    assert difference == exp_list


def test_difference_3():
    difference: list[str] = list(set(list1) - set(list2)) + list(set(list2) - set(list1))
    difference.sort()
    assert difference == exp_list

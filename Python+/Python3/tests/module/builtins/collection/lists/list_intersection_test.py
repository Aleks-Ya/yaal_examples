list1: list[str] = ['a', 'b', 'c']
list2: list[str] = ['x', 'a', 'z', 'c']
exp_list: list[str] = ['a', 'c']


def test_intersection1():
    intersection1: list[str] = list(set(list1).intersection(set(list2)))
    assert sorted(intersection1) == sorted(exp_list)


def test_intersection2():
    intersection2: list[str] = list(set(list1) & set(list2))
    assert sorted(intersection2) == sorted(exp_list)


def test_intersection3():
    intersection3: list[str] = [item for item in list1 if item in list2]
    assert intersection3 == exp_list

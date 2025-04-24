set1: set[str] = {'a', 'b', 'c'}
set2: set[str] = {'x', 'a', 'z', 'c'}
exp_set: set[str] = {'b', 'x', 'z'}


def test_difference_1():
    difference: set[str] = set1 ^ set2
    assert difference == exp_set


def test_difference_2():
    difference: set[str] = {i for i in set1.union(set2) if i not in set1 or i not in set2}
    assert difference == exp_set


def test_difference_3():
    difference: set[str] = (set1 - set2).union(set2 - set1)
    assert difference == exp_set


def test_difference_4():
    difference: set[str] = set1.difference(set2).union(set2.difference(set1))
    assert difference == exp_set

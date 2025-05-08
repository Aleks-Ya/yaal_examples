from typing import Iterable


def test_iterable():
    iterable: Iterable = [1, 2, 3]
    for item in iterable:
        print(item)


def test_sort_iterable():
    iterable: Iterable = [2, 3, 1]
    sorted_iterable: list[int] = sorted(iterable)
    assert sorted_iterable == [1, 2, 3]


def test_sort_iterable_by_property():
    iterable: Iterable = ["aaa", "b", "cc"]
    sorted_iterable: list[int] = sorted(iterable, key=lambda x: len(x))
    assert sorted_iterable == ["b", "cc", "aaa"]

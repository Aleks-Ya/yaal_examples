from typing import List


def test_simple_range():
    r: range = range(2)
    res: List[int] = []
    for num in r:
        res.append(num)
    assert res == [0, 1]


def test_range_with_start():
    r: range = range(1, 5)
    res: List[int] = []
    [res.append(num) for num in r]
    assert res == [1, 2, 3, 4]

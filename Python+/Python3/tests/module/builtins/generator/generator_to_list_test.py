from typing import List


def test_conversion_by_list():
    generator: range = range(3)
    nums: List[int] = list(generator)
    assert nums == [0, 1, 2]


def test_conversion_by_list_comprehension():
    generator: range = range(3)
    nums: List[int] = [x for x in generator]
    assert nums == [0, 1, 2]


def test_conversion_by_list_comprehension_with_filtering():
    generator: range = range(3)
    nums: List[int] = [x for x in generator if x > 1]
    assert nums == [2]

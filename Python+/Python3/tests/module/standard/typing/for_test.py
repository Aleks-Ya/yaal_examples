from typing import List


def test_loop_with_type():
    lst: List[int] = [1, 2, 3]
    result: int = 0
    num: int
    for num in lst:
        result = result + num
    assert result == 6

import itertools
from typing import Sequence, Iterator


def split_sequence(seq: Sequence[int], n: int) -> list[list[int]]:
    it: Iterator[int] = iter(seq)
    return [list(itertools.islice(it, i)) for i in [len(seq) // n + (1 if x < len(seq) % n else 0) for x in range(n)]]


numbers: Sequence[int] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
group_number: int = 3
split: list[list[int]] = split_sequence(numbers, group_number)
assert split == [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]

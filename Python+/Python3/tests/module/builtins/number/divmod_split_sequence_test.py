from typing import Sequence


def split_sequence(seq: Sequence[int], n: int) -> list[Sequence[int]]:
    k, m = divmod(len(seq), n)
    return [seq[i * k + min(i, m):(i + 1) * k + min(i + 1, m)] for i in range(n)]


def test_split_sequence():
    numbers: Sequence[int] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    group_number: int = 3
    split: list[Sequence[int]] = split_sequence(numbers, group_number)
    assert split == [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]

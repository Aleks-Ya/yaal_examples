import itertools
from typing import Sequence


def test_join_sequences():
    s1: Sequence[str] = ("a", "b")
    s2: Sequence[str] = ("x", "z")
    joined: Sequence[str] = list(itertools.chain(s1, s2))
    assert joined == ['a', 'b', 'x', 'z']

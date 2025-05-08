# "frozenset" is an immutable "set"

def test_frozenset():
    act: frozenset[int] = frozenset([1, 2, 3])
    exp: set[int] = {1, 2, 3}
    assert act == exp


def test_frozenset_empty():
    act: frozenset[int] = frozenset()
    exp: set[int] = set()
    assert act == exp

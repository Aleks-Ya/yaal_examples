from typing import Generator, Any


def test_typed_generator():
    g: Generator[int, Any, None] = (x for x in range(3))
    assert list(g) == [0, 1, 2]

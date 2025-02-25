# Create a generator by using "yield" operator
from typing import Any, Generator

import pytest


def generator():
    seq: range = range(3)
    for v in seq:
        yield v * v


def test_generator():
    g: Generator[int, Any, None] = generator()
    assert next(g) == 0  # First value (0*0)
    assert next(g) == 1  # Second value (1*1)
    assert next(g) == 4  # Third value (2*2)
    with pytest.raises(StopIteration):
        next(g)  # No more items in the generator

# Create generator by braces
from typing import Any, Generator


def test_create_generator():
    g: Generator[int, Any, None] = (x * x for x in range(3))
    for i in g:
        print(i)

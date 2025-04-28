from contextlib import contextmanager
from typing import Generator


@contextmanager
def greeting(name: str) -> Generator[str, str, None]:
    try:
        yield f"Dear, {name}!"
    finally:
        pass


def test_context_manager():
    with greeting("John") as text:
        assert text == "Dear, John!"

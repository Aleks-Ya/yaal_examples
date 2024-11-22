def greet(*args: str) -> list[str]:
    return [f"name: {value}" for value in args]


def test_kwargs():
    assert greet("Alice", "John", "Mary") == ['name: Alice', 'name: John', 'name: Mary']

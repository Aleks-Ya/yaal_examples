def greet(**kwargs: str) -> list[str]:
    return [f"{key}: {value}" for key, value in kwargs.items()]


def test_kwargs():
    assert greet(name="Alice", age="30", city="New York") == ['name: Alice', 'age: 30', 'city: New York']

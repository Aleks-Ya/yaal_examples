import pytest


@pytest.fixture(params=["John", "Mary"])
def person(request) -> str:
    name: str = request.param
    return f"Hello, {name}!"


def test_person(person: str):
    assert person in ["Hello, John!", "Hello, Mary!"]

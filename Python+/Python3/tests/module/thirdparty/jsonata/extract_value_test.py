from typing import Any

from jsonata import Jsonata


def test_string():
    data: dict[str, Any] = {"name": "John"}
    expression: str = "$.name"
    assert __extract(data, expression) == 'John'


def __extract(data: dict[str, Any], expression: str) -> Any:
    return Jsonata(expression).evaluate(data)

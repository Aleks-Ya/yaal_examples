from typing import Any

from jsonata import Jsonata


def test_concatenate_strings():
    data: dict[str, Any] = {"name": "John"}
    expression: str = "'Hello, ' & $.name"
    assert __extract(data, expression) == 'Hello, John'


def __extract(data: dict[str, Any], expression: str) -> Any:
    return Jsonata(expression).evaluate(data)

from typing import Any

from module.thirdparty.jsonata.conftest import evaluate


def test_concatenate_strings():
    data: dict[str, Any] = {"name": "John"}
    expression: str = "'Hello, ' & $.name"
    assert evaluate(data, expression) == 'Hello, John'

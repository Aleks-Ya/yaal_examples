from typing import Any

from module.thirdparty.jsonata.conftest import evaluate


def test_string():
    data: dict[str, Any] = {"name": "John"}
    expression: str = "$.name"
    assert evaluate(data, expression) == 'John'

from typing import Any

from module.thirdparty.jsonata.conftest import evaluate


def test_plus():
    data: dict[str, Any] = {"a": 1, "b": 2}
    expression: str = "$.a + $.b"
    assert evaluate(data, expression) == 3


def test_minus():
    data: dict[str, Any] = {"a": 1, "b": 2}
    expression: str = "$.a - $.b"
    assert evaluate(data, expression) == -1


def test_concatenate():
    data: dict[str, Any] = {"a": 1, "b": 2}
    expression: str = "$.a & $.b"
    assert evaluate(data, expression) == '12'

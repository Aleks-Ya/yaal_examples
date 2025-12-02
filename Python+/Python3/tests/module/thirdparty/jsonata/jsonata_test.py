import json
from typing import Any

from jsonata import Jsonata


def test_evaluate_dict():
    data: dict[str, Any] = {"example": [{"value": 4}, {"value": 7}, {"value": 13}]}
    expr: Jsonata = Jsonata("$sum(example.value)")
    result: int = expr.evaluate(data)
    assert result == 24


def test_evaluate_str():
    json_str: str = """{"example": [{"value": 4}, {"value": 7}, {"value": 13}]}"""
    data_dict: dict[str, Any] = json.loads(json_str)
    expr: Jsonata = Jsonata("$sum(example.value)")
    result: int = expr.evaluate(data_dict)
    assert result == 24

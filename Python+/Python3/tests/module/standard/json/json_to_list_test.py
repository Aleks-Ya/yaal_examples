import json
from typing import Any


def test_parse_list_of_strings():
    content: str = '["a", "b", "c"]'
    str_list: list[str] = json.loads(content)
    assert str_list == ["a", "b", "c"]


def test_parse_list_of_maps():
    content: str = '[{"name": "John", "age": 30}, {"name": "Mary", "age": 25}]'
    str_list: list[dict[str, Any]] = json.loads(content)
    assert str_list == [{"name": "John", "age": 30}, {"name": "Mary", "age": 25}]


def test_parse_list_of_maps_with_list():
    content: str = '[{"name": "John", "cities": ["London", "Berlin"]}, {"name": "Mary", "cities": []}]'
    obj_list: list[dict[str, Any]] = json.loads(content)
    assert obj_list == [{"name": "John", "cities": ["London", "Berlin"]},
                        {"name": "Mary", "cities": []}]

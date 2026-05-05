import json
from pprint import pformat


def test_format_json_dict():
    json_dict: dict[str, object] = {
        "key": "value",
        "nested": {"key": "value"},
        "list": [1, 2, 3]
    }
    s: str = pformat(json_dict)
    assert s == "{'key': 'value', 'list': [1, 2, 3], 'nested': {'key': 'value'}}"


def test_format_json_object():
    json_obj: object = json.loads('{"key": "value", "nested": {"key": "value"}, "list": [1, 2, 3]}')
    s: str = pformat(json_obj)
    assert s == "{'key': 'value', 'list': [1, 2, 3], 'nested': {'key': 'value'}}"

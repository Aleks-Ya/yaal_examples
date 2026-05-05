import json
from pprint import pprint


def test_print_json_dict():
    json_dict: dict[str, object] = {
        "key": "value",
        "nested": {"key": "value"},
        "list": [1, 2, 3]
    }
    pprint(json_dict)


def test_print_json_object():
    json_obj: object = json.loads('{"key": "value", "nested": {"key": "value"}, "list": [1, 2, 3]}')
    pprint(json_obj)

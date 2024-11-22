import json


def test_json_value():
    data: str = '{"a": {"b": 7}}'
    json_obj: object = json.loads(data)
    value: object = json_obj.get('a').get('b')
    assert value == 7


def test_all_fields():
    data: str = '{"a": {"b": 7}}'
    json_obj: object = json.loads(data)
    print(json_obj)

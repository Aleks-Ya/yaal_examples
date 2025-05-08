import json

data: str = '{"a": {"b": 7}}'


def test_json_value():
    json_obj: dict = json.loads(data)
    value: int = json_obj.get('a').get('b')
    assert value == 7


def test_json_value_dict():
    json_obj: dict = json.loads(data)
    value: int = json_obj['a']['b']
    assert value == 7


def test_all_fields():
    json_obj: dict = json.loads(data)
    print(json_obj)

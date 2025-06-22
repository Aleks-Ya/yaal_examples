# Construct JSON in memory (without parsing strings and reading files)
import json


def test_dict_to_json():
    dict1: dict[str, object] = {'field1': 1, 'field2': 'abc'}
    json1: str = json.dumps(dict1)
    assert json1 == '{"field1": 1, "field2": "abc"}'


def test_list_to_json():
    list2: list[dict[str, object]] = [{'a': 54}, {'b': 'abc', 'c': 87}]
    json2: str = json.dumps(list2)
    assert json2 == '[{"a": 54}, {"b": "abc", "c": 87}]'


def test_tuple_to_json():
    tuple3: tuple[dict[str, object], dict[str, object]] = ({'a': 54}, {'b': 'abc', 'c': 87})
    json3: str = json.dumps(tuple3)
    assert json3 == '[{"a": 54}, {"b": "abc", "c": 87}]'

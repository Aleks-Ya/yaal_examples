import json


def test_parse_dict_of_lists():
    content: str = '{"London": ["John", "Mary"], "Berlin": ["Mark"]}'
    obj_list: dict[str, str] = json.loads(content)
    assert obj_list == {"London": ["John", "Mary"], "Berlin": ["Mark"]}

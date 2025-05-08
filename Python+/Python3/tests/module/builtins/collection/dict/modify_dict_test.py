from typing import Any


def test_add_item_to_empty_dict():
    empty_dict: dict[str, int] = dict()
    empty_dict['one'] = 1
    empty_dict['two'] = 2
    assert empty_dict == dict(one=1, two=2)


def test_add_item_to_exists_dict():
    n: dict[str, int] = dict(one=1, two=2)
    n['three'] = 3
    assert n == dict(one=1, two=2, three=3)


def test_join_dictionaries():
    a: dict[str, int] = dict(one=1, two=2)
    b: dict[str, int] = dict(one=11, three=3)
    join: dict[str, int] = a.copy()
    join.update(b)
    assert join == dict(one=11, two=2, three=3)


def test_remove_element_from_dict():
    d: dict[str, int] = {'a': 1, 'b': 2}
    del d['a']
    assert d == {'b': 2}


def test_filter_dict():
    e: dict[str, int] = {'a': 1, 'b': -2, 'c': 3}
    filtered: dict[str, int] = {k: v for k, v in e.items() if v > 0}
    assert filtered == {'a': 1, 'c': 3}


def test_filter_dict_using_lambda():
    e: dict[str, int] = {'a': 1, 'b': -2, 'c': 3}
    filtered: dict[Any, Any] = dict(filter(lambda val: val[1] > 0, e.items()))
    assert filtered == {'a': 1, 'c': 3}


def test_update_recursively():
    def update_dict_recursive(original_dict: dict, update_dict: dict) -> None:
        for key, value in update_dict.items():
            if isinstance(value, dict):
                update_dict_recursive(original_dict.get(key, {}), value)
            else:
                original_dict[key] = value

    a: dict[str, dict[str, int]] = {"John": {"car": 10, "house": {"count": 5, "square": 100}},
                                    "Mary": {"car": 2, "house": {"count": 1, "square": 50}}}
    b: dict[str, dict[str, int]] = {"John": {"car": 11, "house": {"square": 200}},
                                    "Mary": {"house": {"count": 2}}}
    update_dict_recursive(a, b)
    assert a == {"John": {"car": 11, "house": {"count": 5, "square": 200}},
                 "Mary": {"car": 2, "house": {"count": 2, "square": 50}}}

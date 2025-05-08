from typing import Any, Optional


def test_empty_dict():
    empty: dict[Any, Any] = {}
    assert len(empty) == 0


def test_dict_creation():
    a: dict[str, int] = dict(one=1, two=2, three=3)
    b: dict[str, int] = {'one': 1, 'two': 2, 'three': 3}
    c: dict[Any, Any] = dict(zip(['one', 'two', 'three'], [1, 2, 3]))
    d: dict[str, int] = dict([('two', 2), ('one', 1), ('three', 3)])
    e: dict[str, int] = dict({'three': 3, 'one': 1, 'two': 2})
    assert a == b
    assert b == c
    assert c == d
    assert d == e


def test_dict_from_list_of_tuples():
    strings: list[str] = ['a', 'bb', 'ccc']
    d: dict[str, int] = dict([(s, len(s)) for s in strings])
    assert d == {'a': 1, 'bb': 2, 'ccc': 3}


def test_none_key():
    d: dict[Optional[str], int] = {}
    assert d == {}
    d[None] = 0
    d["a"] = 1
    assert d == {None: 0, 'a': 1}


def test_none_value():
    d: dict[str, Optional[int]] = {}
    assert d == {}
    d["a"] = 0
    d["b"] = None
    assert d == {'a': 0, 'b': None}


def test_none_key_and_value():
    d: dict[Optional[str], Optional[int]] = {}
    assert d == {}
    d["a"] = 0
    d["b"] = None
    d[None] = None
    assert d == {None: None, 'a': 0, 'b': None}

from typing import Any

from pytest import raises


def test_get_existing_key():
    d: dict[str, int] = dict(one=1, two=2)
    assert d['one'] == 1
    assert d.get('one') == 1


def test_get_absent_key():
    d: dict[Any, Any] = dict()
    assert d.get('absent') is None
    with raises(KeyError) as ex:
        v = d['absent']
    assert ex.value.args[0] == "absent"


def test_contains_key():
    d: dict[str, int] = dict(one=1, two=2)
    assert 'one' in d
    assert 'three' not in d


def test_size_key():
    d: dict[str, int] = dict(one=1, two=2)
    assert len(d) == 2


def test_nested_dict():
    d: dict[str, dict[str, str]] = dict(address=dict(city="London"))
    assert d['address']['city'] == "London"

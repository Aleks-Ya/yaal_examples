from typing import Any


def test_remove_duplicates_loose_order():
    original: list[str] = ['a', 'b', 'c', 'a', 'c']
    distinct: list[str] = list(set(original))
    assert set(distinct) == {'a', 'b', 'c'}  # Order is not guaranteed


def test_remove_duplicates_preserve_order_by_dict():
    original: list[str] = ['a', 'b', 'c', 'a', 'c']
    distinct: list[str] = list(dict.fromkeys(original))
    assert distinct == ['a', 'b', 'c']  # Order is preserved


def test_remove_duplicates_preserve_order_by_set():
    original: list[str] = ['a', 'b', 'c', 'a', 'c']
    distinct: list[str] = __remove_duplicates(original)
    assert distinct == ['a', 'b', 'c']  # Order is preserved


def __remove_duplicates(original: list[Any]) -> list[Any]:
    seen: set[Any] = set()
    return [x for x in original if not (x in seen or seen.add(x))]

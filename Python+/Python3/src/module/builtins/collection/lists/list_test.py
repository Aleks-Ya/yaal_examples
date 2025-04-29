from typing import Any

from pytest import raises


def test_empty_list():
    e: list[Any] = []
    assert len(e) == 0


def test_single_element_list():
    single_element_list: list[int] = [25]
    assert single_element_list[0] == 25


def test_contains_element():
    symbols: list[str] = ['a', 'b', 'c']
    assert 'b' in symbols
    assert 'z' not in symbols


def test_get_element_by_index():
    squares: list[int] = [1, 4, 9, 16, 25]
    assert squares[1] == 4
    assert squares[2] == 9
    assert squares[-2] == 16  # 2nd from the end


def test_index_out_of_range():
    with raises(IndexError, match="list index out of range"):
        numbers: list[int] = [1, 2, 3]
        element: int = numbers[3]
        print(element)


def test_copy():
    squares: list[int] = [1, 4, 9, 16, 25]
    copy: list[int] = squares[:]
    assert copy == squares


def test_replace():
    lst: list[int] = [1, 3, 3]
    lst[1] = 2
    assert lst == [1, 2, 3]


def test_nested_lists():
    lst: list[list[int] | list[str]] = [[1, 2], ['a', 'b']]
    assert len(lst) == 2


def test_is_empty():
    lst: list[Any] = []
    assert not lst


def test_is_not_empty():
    lst: list[int] = [1]
    assert lst


def test_find_element_index():
    lst: list[str] = ['a', 'b', 'c']
    assert lst.index('b') == 1


def test_transform_list_elements():
    lst: list[str] = ['a', 'b', 'c']
    result_map = map(lambda symbol: symbol.upper(), lst)
    result_list: list[Any] = list(result_map)
    assert result_list == ['A', 'B', 'C']


def test_flat_map():
    list_of_lists: list[list[str]] = [['a', 'b'], ['c', 'd'], ['e']]
    flattened_list: list[str] = [item for sublist in list_of_lists for item in sublist]
    assert flattened_list == ['a', 'b', 'c', 'd', 'e']

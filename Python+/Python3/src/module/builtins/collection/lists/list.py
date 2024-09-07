def test_empty_list():
    e = []
    assert len(e) == 0


def test_single_element_list():
    single_element_list = [25]
    assert single_element_list[0] == 25


def test_contains_element():
    symbols = ['a', 'b', 'c']
    assert 'b' in symbols
    assert 'z' not in symbols


def test_get_element_by_index():
    squares = [1, 4, 9, 16, 25]
    assert squares[1] == 4
    assert squares[-2] == 16


def test_copy():
    squares = [1, 4, 9, 16, 25]
    copy = squares[:]
    assert copy == squares


def test_replace():
    lst = [1, 3, 3]
    lst[1] = 2
    assert lst == [1, 2, 3]


def test_nested_lists():
    lst = [[1, 2], ['a', 'b']]
    assert len(lst) == 2


def test_is_empty():
    lst = []
    assert not lst


def test_is_not_empty():
    lst = [1]
    assert lst


def test_find_element_index():
    lst = ['a', 'b', 'c']
    assert lst.index('b') == 1


def test_transform_list_elements():
    lst = ['a', 'b', 'c']
    result_map = map(lambda symbol: symbol.upper(), lst)
    result_list = list(result_map)
    assert result_list == ['A', 'B', 'C']


def test_flat_map():
    list_of_lists = [['a', 'b'], ['c', 'd'], ['e']]
    flattened_list = [item for sublist in list_of_lists for item in sublist]
    assert flattened_list == ['a', 'b', 'c', 'd', 'e']

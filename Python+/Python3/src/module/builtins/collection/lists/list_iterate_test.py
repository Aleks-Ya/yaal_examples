def test_iterate_list():
    lst: list[str] = ['a', 'b', 'c']
    result: str = ''
    for char in lst:
        result = result + char
    assert result == 'abc'


def test_iterate_list_with_indexes():
    lst: list[str] = ['a', 'b', 'c']
    result: str = ''
    for i, char in enumerate(lst):
        result = result + str(i) + char
    assert result == '0a1b2c'


def test_iterate_list_by_two_elements():
    lst: list[str] = ['a', 'b', 'c', 'd', 'e', 'f']
    result: list[str] = []
    for i in range(0, len(lst), 2):
        element1: str = lst[i]
        element2: str = lst[i + 1]
        result.append(f"{element1} {element2}")
    assert result == ["a b", "c d", "e f"]


def test_count_elements_by_condition():
    lst: list[str] = ['a', 'bb', 'ccc', 'dddd', 'eeeee']
    long_count: int = len(list(filter(lambda x: len(x) > 3, lst)))
    assert long_count == 2

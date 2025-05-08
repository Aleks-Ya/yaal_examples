def test_dict_to_key_list():
    d: dict[str, int] = {'a': 1, 'b': 2, 'c': 3}
    assert list(d.keys()) == ['a', 'b', 'c']


def test_dict_to_value_list():
    d: dict[str, int] = {'a': 1, 'b': 2, 'c': 3}
    assert list(d.values()) == [1, 2, 3]


def test_list_to_dict():
    l: list[str] = ['a', 'bb', 'ccc']
    d: dict[str, int] = {element: len(element) for element in l}
    assert d == {'a': 1, 'bb': 2, 'ccc': 3}

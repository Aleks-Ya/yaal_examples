def test_sort_ascending():
    l: list[str] = ['a', 'z', 'b', 't']
    l.sort()
    assert l == ['a', 'b', 't', 'z']


def test_sort_descending():
    l: list[str] = ['a', 'z', 'b', 't']
    l.sort(reverse=True)
    assert l == ['z', 't', 'b', 'a']

def test_split_by_symbol():
    assert 'a,b'.split(',') == ['a', 'b']
    assert 'ab'.split(',') == ['ab']


def test_split_by_whitespace():
    assert 'a b c'.split() == ['a', 'b', 'c']

def test_split():
    assert 'a,b'.split(',') == ['a', 'b']
    assert 'ab'.split(',') == ['ab']

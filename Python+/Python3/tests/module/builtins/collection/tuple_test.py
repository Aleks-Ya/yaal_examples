def test_empty_tuple():
    t = ()
    assert len(t) == 0


def test_single_value_tuple():
    t = (50,)
    assert t[0] == 50


def test_multi_value_tuple():
    t = ('a', 1, 'b', 2)
    assert t[0] == 'a'
    assert t[1] == 1
    assert t[2] == 'b'
    assert t[3] == 2

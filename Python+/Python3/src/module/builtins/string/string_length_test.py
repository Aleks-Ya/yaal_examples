def test_length():
    assert len('abc') == 3


def test_length_in_bytes():
    assert len('abc'.encode()) == 3
    assert len('∑'.encode()) == 3
    assert len('∑￡'.encode()) == 6

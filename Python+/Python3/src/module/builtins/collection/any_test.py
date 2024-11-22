def test_any_numbers():
    assert any([1, 2, 3, 4, 5]) is True


def test_any_not_none():
    assert any([]) is False
    assert any([None, None]) is False
    assert any([1, None]) is True


def test_any_true():
    assert any([True, True]) is True
    assert any([True, False]) is True
    assert any([False, False]) is False

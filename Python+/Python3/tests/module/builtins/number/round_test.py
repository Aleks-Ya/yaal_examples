def test_round():
    assert round(987.23456, -1) == 990
    assert round(987.23456, 0) == 987.0
    assert round(987.23456, 1) == 987.2
    assert round(987.23456, 2) == 987.23
    assert round(987.23456, 3) == 987.235

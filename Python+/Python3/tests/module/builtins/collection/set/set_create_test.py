def test_create_empty_set():
    s: set[int] = set()
    assert len(s) == 0


def test_create_set():
    s: set[int] = {1, 2}
    assert len(s) == 2


def test_no_duplicates():
    s: set[int] = {1, 2, 1}
    assert s == {1, 2}

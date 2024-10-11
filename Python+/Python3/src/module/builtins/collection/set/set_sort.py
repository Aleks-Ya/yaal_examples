def test_sort_set():
    s: set[int] = {3, 2, 1}
    l: list[int] = sorted(s)
    assert l == [1, 2, 3]

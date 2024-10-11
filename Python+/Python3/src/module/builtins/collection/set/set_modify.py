from joblib.testing import raises


def test_add_element():
    s: set[int] = {1, 2}
    s.add(3)
    assert s == {1, 2, 3}


def test_remove_existent_element():
    s: set[int] = {1, 2, 3}
    s.remove(2)
    assert s == {1, 3}


def test_remove_absent_element():
    s: set[int] = {1, 2, 3}
    with raises(KeyError):
        s.remove(4)


def test_remove_absent_element_safety():
    s: set[int] = {1, 2, 3}
    s.discard(4)
    s.discard(2)
    assert s == {1, 3}


def test_join_sets_by_update():
    s1: set[int] = {10, 11}
    s2: set[int] = {1, 2}
    s1.update(s2)
    assert s1 == {10, 11, 1, 2}


def test_join_sets_by_union():
    s1: set[int] = {10, 11}
    s2: set[int] = {1, 2}
    s3: set[int] = s1.union(s2)
    assert s3 == {10, 11, 1, 2}


def test_join_sets_by_update_operator():
    s1: set[int] = {10, 11}
    s2: set[int] = {1, 2}
    s1 |= s2
    assert s1 == {10, 11, 1, 2}

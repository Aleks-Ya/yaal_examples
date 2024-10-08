from typing import List


def test_list_concatenation():
    c: List[int] = [1, 2] + [3, 4]
    assert c == [1, 2, 3, 4]


def test_list_append():
    c: List[int] = [1, 2]
    old_id: int = id(c)
    c += [3, 4]
    new_id: int = id(c)
    assert c == [1, 2, 3, 4]
    assert new_id == old_id  # old list was appended instead of creating new instance


def test_element_append():
    app: List[int] = [1, 2]
    app.append(3)
    assert app == [1, 2, 3]

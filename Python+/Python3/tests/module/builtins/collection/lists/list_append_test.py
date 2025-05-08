def test_join_lists_in_new_list():
    c: list[int] = [1, 2] + [3, 4]
    assert c == [1, 2, 3, 4]


def test_add_list_to_list():
    list1: list[int] = [1, 2]
    list2: list[int] = [3, 4]
    list1.extend(list2)
    assert list1 == [1, 2, 3, 4]


def test_list_append():
    c: list[int] = [1, 2]
    old_id: int = id(c)
    c += [3, 4]
    new_id: int = id(c)
    assert c == [1, 2, 3, 4]
    assert new_id == old_id  # old list was appended instead of creating new instance


def test_append_one_element_to_list():
    app: list[int] = [1, 2]
    app.append(3)
    assert app == [1, 2, 3]

def test_list_change():
    numbers: list[int] = [1, 2, 3]
    list_changed: list[int] = [num * 2 for num in numbers]
    assert list_changed == [2, 4, 6]


def test_list_filter():
    numbers: list[int] = [1, 2, 3]
    list_filtered: list[int] = [num for num in numbers if num > 1]
    assert list_filtered == [2, 3]

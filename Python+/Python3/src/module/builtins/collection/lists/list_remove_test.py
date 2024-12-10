def test_remove_elements_by_condition():
    numbers: list[int] = [1, -5, 2, -6, 0]
    positive_numbers: list[int] = [number for number in numbers if number > 0]
    assert positive_numbers == [1, 2]


def test_remove_elements_by_condition_2():
    numbers: list[int] = [1, -5, 2, -6, 0]
    numbers[:] = [number for number in numbers if number > 0]
    assert numbers == [1, 2]

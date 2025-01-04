def test_slicing():
    squares: list[int] = [1, 4, 9, 16, 25]
    assert squares[1:3] == [4, 9]
    assert squares[:3] == [1, 4, 9]
    assert squares[3:] == [16, 25]
    assert squares[-2:] == [16, 25]
    assert squares[:-2] == [1, 4, 9]
    assert squares[:1_000] == [1, 4, 9, 16, 25]


def test_list_slicing_into_chunks():
    numbers: list[int] = [1, 2, 3, 4, 5]
    c: int = 2
    expected_result: list[list[int]] = [[1, 2], [3, 4], [5]]
    list_of_slices: list[list[int]] = [numbers[i:i + c] for i in range(0, len(numbers), c)]
    assert list_of_slices == expected_result

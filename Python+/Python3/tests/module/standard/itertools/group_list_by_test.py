from itertools import groupby


def test_group_by_tuples():
    people: list[tuple[str, str]] = [("Paris", "John"), ("Berlin", "Mary"), ("Paris", "Mark")]
    people.sort(key=lambda x: x[0])
    grouped: groupby[str, tuple[str, str]] = groupby(people, key=lambda x: x[0])
    result: dict[str, list[tuple[str, str]]] = {k: list(v) for k, v in grouped}
    assert result == {
        "Berlin": [("Berlin", "Mary")],
        "Paris": [("Paris", "John"), ("Paris", "Mark")]
    }


def test_group_by_numbers():
    numbers: list[int] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    numbers.sort(key=lambda x: x % 2)
    grouped: dict[int, list[int]] = {k: list(g) for k, g in groupby(numbers, key=lambda x: x % 2)}
    odd_numbers: list[int] = grouped.get(1, [])
    even_numbers: list[int] = grouped.get(0, [])
    assert odd_numbers == [1, 3, 5, 7, 9]
    assert even_numbers == [2, 4, 6, 8, 10]


def test_count_number_of_elements_in_list():
    cities: list[str] = ["Paris", "Berlin", "Paris"]
    cities.sort()
    grouped: groupby[str, str] = groupby(cities)
    result: dict[str, int] = {k: len(list(v)) for k, v in grouped}
    assert result == {
        "Berlin": 1,
        "Paris": 2
    }

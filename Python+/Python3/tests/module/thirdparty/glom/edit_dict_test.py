from glom import assign


def test_assign():
    data: dict[str, dict[str, int]] = {"John": {"age": 30}}
    assign(data, 'John.age', 35)
    assert data == {'John': {'age': 35}}

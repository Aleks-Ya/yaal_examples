import dpath


def test_set():
    data: dict[str, dict[str, int]] = {"John": {"age": 30}}
    dpath.set(data, 'John/age', 35)
    assert data == {'John': {'age': 35}}

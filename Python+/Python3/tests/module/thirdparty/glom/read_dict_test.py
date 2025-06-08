from glom import glom


def test_read():
    data: dict[str, dict[str, int]] = {"John": {"age": 30}}
    age: int = glom(data, 'John.age')
    assert age == 30

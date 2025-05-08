def test_get_object_id():
    obj1: str = "abc"
    id_1: int = id(obj1)
    print(id_1)

    obj2: str = "abc"
    id_2: int = id(obj2)
    print(id_2)

    assert id_1 == id_2

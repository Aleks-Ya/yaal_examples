import pickle


def test_serialize_and_deserialize_from_bytes():
    exp_data: list[int] = [1, 3, 5]
    serialized: bytes = pickle.dumps(exp_data)
    act_data: list[int] = pickle.loads(serialized)
    assert act_data == exp_data

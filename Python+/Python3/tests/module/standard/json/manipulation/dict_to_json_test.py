import json


def test_serialize():
    sizes: dict[str, int] = {"1KB": 1024, "2KB": 2048}
    json_str: str = json.dumps(sizes)
    assert json_str == '{"1KB": 1024, "2KB": 2048}'


def test_deserialize():
    json_str: str = '{"1KB": 1024, "2KB": 2048}'
    act_sizes: dict[str, int] = json.loads(json_str)
    exp_sizes: dict[str, int] = {"1KB": 1024, "2KB": 2048}
    assert act_sizes == exp_sizes

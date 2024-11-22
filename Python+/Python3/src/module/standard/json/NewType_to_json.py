import json
from typing import NewType

SizeBytes = NewType("SizeBytes", int)
SizeStr = NewType("SizeStr", str)


def test_serialize():
    sizes: dict[SizeStr, SizeBytes] = {
        SizeStr("1KB"): SizeBytes(1024),
        SizeStr("2KB"): SizeBytes(2048)
    }
    json_str: str = json.dumps(sizes)
    assert json_str == '{"1KB": 1024, "2KB": 2048}'


def test_deserialize():
    json_str: str = '{"1KB": 1024, "2KB": 2048}'
    act_sizes: dict[SizeStr, SizeBytes] = json.loads(json_str)
    exp_sizes: dict[SizeStr, SizeBytes] = {
        SizeStr("1KB"): SizeBytes(1024),
        SizeStr("2KB"): SizeBytes(2048)
    }
    assert act_sizes == exp_sizes

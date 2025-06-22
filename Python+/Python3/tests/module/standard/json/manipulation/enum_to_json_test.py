import json
from enum import Enum


class SizeType(int, Enum):
    TOTAL = 1
    FILES = 2


def test_serialize():
    sizes: dict[SizeType, int] = {SizeType.TOTAL: 2048, SizeType.FILES: 1024}
    json_str: str = json.dumps(sizes)
    assert json_str == '{"1": 2048, "2": 1024}'

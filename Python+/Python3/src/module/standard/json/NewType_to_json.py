import json
from typing import NewType
import unittest

SizeBytes = NewType("SizeBytes", int)
SizeStr = NewType("SizeStr", str)


class TestNewTypeToJson(unittest.TestCase):

    def test_serialize(self):
        sizes: dict[SizeStr, SizeBytes] = {
            SizeStr("1KB"): SizeBytes(1024),
            SizeStr("2KB"): SizeBytes(2048)
        }
        json_str: str = json.dumps(sizes)
        self.assertEqual('{"1KB": 1024, "2KB": 2048}', json_str)

    def test_deserialize(self):
        json_str: str = '{"1KB": 1024, "2KB": 2048}'
        act_sizes: dict[SizeStr, SizeBytes] = json.loads(json_str)
        exp_sizes: dict[SizeStr, SizeBytes] = {
            SizeStr("1KB"): SizeBytes(1024),
            SizeStr("2KB"): SizeBytes(2048)
        }
        self.assertEqual(exp_sizes, act_sizes)


if __name__ == "__main__":
    unittest.main()

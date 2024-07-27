import json
import unittest
from enum import Enum


class SizeType(int, Enum):
    TOTAL = 1
    FILES = 2


class TestEnumToJson(unittest.TestCase):

    def test_serialize(self):
        sizes: dict[SizeType, int] = {SizeType.TOTAL: 2048, SizeType.FILES: 1024}
        json_str: str = json.dumps(sizes)
        self.assertEqual('{"1": 2048, "2": 1024}', json_str)


if __name__ == "__main__":
    unittest.main()

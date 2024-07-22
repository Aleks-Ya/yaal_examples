import unittest
import json


class TestDictSerialization(unittest.TestCase):
    def setUp(self):
        self.dictionary: dict[str, int] = {'one': 1, 'two': 2, 'three': 3}

    def test_json_dumps(self):
        serialized: str = json.dumps(self.dictionary)
        self.assertEqual(serialized, '{"one": 1, "two": 2, "three": 3}')

    def test_json_loads(self):
        serialized: str = json.dumps(self.dictionary)
        deserialized: dict[str, int] = json.loads(serialized)
        self.assertEqual(deserialized, self.dictionary)


if __name__ == '__main__':
    unittest.main()

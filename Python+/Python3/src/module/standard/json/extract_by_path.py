import unittest
import json


class TestExtractByPath(unittest.TestCase):
    def setUp(self):
        self.data: str = '{"a": {"b": 7}}'
        self.json_obj: dict[str, object] = json.loads(self.data)

    def test_json_value(self):
        value: int = self.json_obj.get('a').get('b')
        self.assertEqual(value, 7)

    def test_all_fields(self):
        print(self.json_obj)


if __name__ == '__main__':
    unittest.main()

import unittest
from typing import Dict


class TestTypedDict(unittest.TestCase):

    def test_typed_dict(self):
        error_1: RuntimeError = RuntimeError()
        typed_dict: Dict[str, RuntimeError] = {'error': error_1}
        self.assertEqual(typed_dict['error'], error_1)

        error_2: RuntimeError = RuntimeError()
        typed_dict_2: dict[str, RuntimeError] = {'error': error_2}
        self.assertEqual(typed_dict_2['error'], error_2)


if __name__ == '__main__':
    unittest.main()

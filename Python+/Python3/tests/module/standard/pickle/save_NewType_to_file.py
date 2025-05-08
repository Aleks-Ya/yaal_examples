import unittest
import pickle
import tempfile
from typing import NewType

SizeBytes = NewType("SizeBytes", int)
SizeStr = NewType("SizeStr", str)


class TestPickleNewType(unittest.TestCase):
    def test_dump_load_new_type(self):
        filename: str = tempfile.mktemp()
        exp_sizes: dict[SizeStr, SizeBytes] = {
            SizeStr("1KB"): SizeBytes(1024),
            SizeStr("2KB"): SizeBytes(2048)
        }
        pickle.dump(exp_sizes, open(filename, 'wb'))
        act_sizes: dict[SizeStr, SizeBytes] = pickle.load(open(filename, 'rb'))
        self.assertEqual(exp_sizes, act_sizes)


if __name__ == '__main__':
    unittest.main()

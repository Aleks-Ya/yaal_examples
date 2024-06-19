import sys
import unittest


class TestStringSize(unittest.TestCase):
    def test_get_size_of(self):
        self.assertEqual(42, sys.getsizeof("a"))


if __name__ == '__main__':
    unittest.main()

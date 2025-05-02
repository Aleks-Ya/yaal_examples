import unittest

from utils import to_upper_case


class UtilsTest(unittest.TestCase):
    def test_utils(self):
        self.assertEqual(to_upper_case("abc"), "ABC")


if __name__ == '__main__':
    unittest.main()

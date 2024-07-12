import unittest

import humanfriendly
from humanfriendly import InvalidSize


class TestHumanFriendly(unittest.TestCase):

    def test_parse_size(self):
        self.assertEqual(100_000, humanfriendly.parse_size("100 KB"))
        self.assertEqual(100_000, humanfriendly.parse_size("100KB"))
        self.assertEqual(100, humanfriendly.parse_size("100B"))
        self.assertEqual(100_000_000, humanfriendly.parse_size("100MB"))
        self.assertEqual(100_000, humanfriendly.parse_size("100kb"))

    def test_parse_size_empty(self):
        with self.assertRaises(InvalidSize):
            humanfriendly.parse_size("")

    def test_parse_size_invalid(self):
        with self.assertRaises(InvalidSize):
            humanfriendly.parse_size("abc")

    def test_format_size(self):
        self.assertEqual("100 KB", humanfriendly.format_size(100_000))
        self.assertEqual("1 MB", humanfriendly.format_size(1_000_000))
        self.assertEqual("1.00 MB", humanfriendly.format_size(1_000_000, keep_width=True))
        self.assertEqual("1.1 MB", humanfriendly.format_size(1_100_000))


if __name__ == '__main__':
    unittest.main()

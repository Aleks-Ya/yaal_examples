import unittest

import humanize


class HumanizeTestCase(unittest.TestCase):

    def test_convert_bytes_to_strings(self):
        self.assertEqual(humanize.naturalsize(1_000_000), "1.0 MB")
        self.assertEqual(humanize.naturalsize(1_000_000, binary=True), "976.6 KiB")
        self.assertEqual(humanize.naturalsize(1_000_000, gnu=True), "976.6K")


if __name__ == '__main__':
    unittest.main()

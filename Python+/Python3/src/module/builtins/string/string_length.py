import unittest


class TestStringLength(unittest.TestCase):
    def test_length(self):
        self.assertEqual(3, len('abc'))

    def test_length_in_bytes(self):
        self.assertEqual(3, len('abc'.encode()))
        self.assertEqual(3, len('∑'.encode()))
        self.assertEqual(6, len('∑￡'.encode()))


if __name__ == '__main__':
    unittest.main()

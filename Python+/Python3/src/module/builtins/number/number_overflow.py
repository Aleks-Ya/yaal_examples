import unittest


class TestOverflowNumbers(unittest.TestCase):
    def test_overflow(self):
        a: float = 770224622.3
        self.assertEqual(a, 770224622.3)

        b: float = a * 10000000
        self.assertEqual(b, 7702246223000000.0)

        b: float = a * 100000000
        self.assertEqual(b, 7.702246223e+16)


if __name__ == '__main__':
    unittest.main()

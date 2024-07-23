import unittest


class TestIsDigit(unittest.TestCase):
    def test_digit_check(self):
        self.assertTrue('1'.isdigit())
        self.assertTrue('1234567890'.isdigit())
        self.assertFalse('-1'.isdigit())
        self.assertFalse('1.1'.isdigit())
        self.assertFalse(''.isdigit())
        self.assertFalse(' '.isdigit())


if __name__ == '__main__':
    unittest.main()

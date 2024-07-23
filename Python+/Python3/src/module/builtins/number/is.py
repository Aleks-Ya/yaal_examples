import unittest


class TestIsNumberType(unittest.TestCase):
    def test_float_is_int(self):
        f = 123.4
        self.assertFalse(f.is_integer())

    def test_variable_contains_float(self):
        f = 1.1
        self.assertTrue(isinstance(f, float))

        i = 1
        self.assertFalse(isinstance(i, float))


if __name__ == '__main__':
    unittest.main()

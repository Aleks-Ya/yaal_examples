import unittest


class TestConvertNumbers(unittest.TestCase):

    def test_float_to_int(self):
        i = int(123.4)
        self.assertEqual(i, 123)

    def test_non_num_exception(self):
        success = False
        try:
            n = 'abc'
            int(n)
        except ValueError:
            success = True
        self.assertTrue(success)


if __name__ == '__main__':
    unittest.main()

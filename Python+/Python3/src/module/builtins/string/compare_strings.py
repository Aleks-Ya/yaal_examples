import unittest


class TestCompareStrings(unittest.TestCase):

    def test_string_contains_substring(self):
        s = 'abc'
        self.assertIn('b', s)
        self.assertNotIn('z', s)

    def test_is_empty(self):
        s = ''
        is_empty = not s
        self.assertTrue(is_empty)

    def test_is_not_empty(self):
        s = 'a'
        self.assertTrue(s)

    def test_is_not_None_and_not_empty(self):
        s = None
        self.assertFalse(s)

        s = ''
        self.assertFalse(s)

        s = 'a'
        self.assertTrue(s)


if __name__ == '__main__':
    unittest.main()

import unittest


class TestException(unittest.TestCase):

    def test_split(self):
        with self.assertRaises(TypeError):
            # noinspection PyTypeChecker
            'abc'.split(2)

    def test_assert_exception(self):
        with self.assertRaises(TypeError) as ctx:
            # noinspection PyTypeChecker
            'abc'.split(2)
        e: TypeError = ctx.exception
        self.assertIsInstance(e, TypeError)
        self.assertEqual(str(e), 'must be str or None, not int')

    def test_assert_exception_regex(self):
        with self.assertRaisesRegex(TypeError, 'must be str or None, not .*'):
            # noinspection PyTypeChecker
            'abc'.split(2)


if __name__ == '__main__':
    unittest.main()

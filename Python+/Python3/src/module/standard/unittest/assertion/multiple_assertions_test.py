import unittest


class TestMultipleAssertions(unittest.TestCase):

    def test_multiple_assertions(self):
        with self.subTest("Subtest 1"):
            self.assertEqual(1, 2)
        with self.subTest("Subtest 2"):
            self.assertEqual(2, 3)
        with self.subTest("Subtest 3"):
            self.assertEqual(2, 2)

    def _nested_sub_test(self, num1, num2):
        with self.subTest(f"Nested subtest {num1}-{num2}"):
            self.assertEqual(num1, num2)

    def test_nested_sub_tests(self):
        with self.subTest("Subtest 1"):
            self._nested_sub_test(1, 2)
            self._nested_sub_test(4, 5)
            self._nested_sub_test(6, 6)
        with self.subTest("Subtest 2"):
            self._nested_sub_test(1, 2)
            self._nested_sub_test(4, 5)
            self._nested_sub_test(6, 6)


if __name__ == '__main__':
    unittest.main()

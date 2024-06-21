import unittest


class TestTypedList(unittest.TestCase):
    def test_typed_list(self):
        from typing import List

        typed_list: List[RuntimeError] = [RuntimeError()]

        print('Append')
        new_element: RuntimeError = RuntimeError("#2")
        typed_list.append(new_element)
        self.assertIn(new_element, typed_list)  # Assertion for 'appending' test

        print('Iterating List')
        str_list: List[str] = ['a', 'b']
        for expected_s in str_list:
            with self.subTest(expected_s=expected_s):
                self.assertIn(expected_s, str_list)  # Assertion for each 'iteration' test


if __name__ == "__main__":
    unittest.main()

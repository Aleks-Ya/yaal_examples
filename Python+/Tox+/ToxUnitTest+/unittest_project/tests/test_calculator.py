import unittest

from calculator.calculator import summarize


class CalculatorTestCase(unittest.TestCase):

    def test_summarize(self):
        self.assertEqual(5, summarize(2, 3))


if __name__ == '__main__':
    unittest.main()

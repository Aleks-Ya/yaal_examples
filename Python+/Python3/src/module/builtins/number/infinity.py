import math
import unittest


class TestInfinity(unittest.TestCase):
    def test_instantiate_infinity(self):
        plus_infinity_1: float = float("inf")
        plus_infinity_2: float = float("+inf")
        plus_infinity_3: float = math.inf

        minus_infinity_1: float = float("-inf")
        minus_infinity_2: float = -math.inf

        self.assertTrue(math.isinf(plus_infinity_1))
        self.assertTrue(math.isinf(plus_infinity_2))
        self.assertTrue(math.isinf(plus_infinity_3))
        self.assertTrue(math.isinf(minus_infinity_1))
        self.assertTrue(plus_infinity_1 > minus_infinity_2)
        self.assertTrue(plus_infinity_3 > minus_infinity_2)

    def test_int_infinity(self):
        with self.assertRaises(OverflowError):
            float_infinity: float = float("inf")
            int(float_infinity)


if __name__ == '__main__':
    unittest.main()

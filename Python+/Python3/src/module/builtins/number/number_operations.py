import unittest


class TestNumberOperations(unittest.TestCase):
    def test_sum(self):
        a = 1 + 2
        self.assertEqual(a, 3)

    def test_floor_division(self):
        b = 10 // 3
        self.assertEqual(b, 3)

    def test_modulo(self):
        c = 10 % 3
        self.assertEqual(c, 1)

    def test_power(self):
        d = 5 ** 2
        self.assertEqual(d, 25)

    def test_floating_point_operations(self):
        e = 5.2 * .3
        self.assertAlmostEqual(e, 1.56)

    def test_number_of_digits_in_int(self):
        f = 12345
        n = len(str(f))
        self.assertEqual(n, 5)


if __name__ == '__main__':
    unittest.main()

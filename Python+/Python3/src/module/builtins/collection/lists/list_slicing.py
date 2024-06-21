import unittest


class TestListSlicing(unittest.TestCase):
    def test_slicing(self):
        squares: list[int] = [1, 4, 9, 16, 25]
        self.assertEqual(squares[1:3], [4, 9])
        self.assertEqual(squares[:3], [1, 4, 9])
        self.assertEqual(squares[3:], [16, 25])
        self.assertEqual(squares[-2:], [16, 25])
        self.assertEqual(squares[:-2], [1, 4, 9])
        self.assertEqual(squares[:1_000], [1, 4, 9, 16, 25])

    def test_list_slicing_into_chunks(self):
        numbers: list[int] = [1, 2, 3, 4, 5]
        c: int = 2
        expected_result: list[list[int]] = [[1, 2], [3, 4], [5]]
        list_of_slices = [numbers[i:i + c] for i in range(0, len(numbers), c)]
        self.assertEqual(list_of_slices, expected_result)


if __name__ == "__main__":
    unittest.main()

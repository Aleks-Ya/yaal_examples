import unittest


class TestNumberFormatting(unittest.TestCase):

    def test_thousands_comma_split(self):
        i: float = 123456.3
        f: str = f"{i:,}"
        self.assertEqual(f, '123,456.3')

    def test_thousands_space_split(self):
        i: float = 123456.3
        f: str = f"{i:,}".replace(',', ' ')
        self.assertEqual(f, '123 456.3')

    def test_padding(self):
        i: int = 123
        f: str = f"a{i:10,}b"
        self.assertEqual(f, 'a       123b')

    def test_padding_with_leading_zeros(self):
        i: int = 123
        f: str = f"a{i:010}b"
        self.assertEqual(f, 'a0000000123b')

    def test_float_to_percent(self):
        i: float = 0.756
        p: int = round(i * 100)
        f: str = f"{p}%"
        self.assertEqual(f, '76%')


if __name__ == '__main__':
    unittest.main()

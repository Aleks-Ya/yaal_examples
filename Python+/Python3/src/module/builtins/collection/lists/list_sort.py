import unittest


class TestSort(unittest.TestCase):

    def test_sort_ascending(self):
        l: list[str] = ['a', 'z', 'b', 't']
        l.sort()
        self.assertEqual(l, ['a', 'b', 't', 'z'])

    def test_sort_descending(self):
        l: list[str] = ['a', 'z', 'b', 't']
        l.sort(reverse=True)
        self.assertEqual(l, ['z', 't', 'b', 'a'])


if __name__ == '__main__':
    unittest.main()

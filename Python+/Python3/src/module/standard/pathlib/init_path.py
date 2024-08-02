import unittest
from pathlib import Path


class TestInitPath(unittest.TestCase):
    def test_path_creation(self):
        p = Path('/', 'tmp', 'work', 'data.txt')
        self.assertEqual(str(p), '/tmp/work/data.txt')


if __name__ == '__main__':
    unittest.main()

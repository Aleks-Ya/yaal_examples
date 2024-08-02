import unittest
from pathlib import Path


class TestPathJoin(unittest.TestCase):
    def test_join(self):
        parent: Path = Path('/parent/dir/')
        child: Path = parent.joinpath('child.txt')
        self.assertEqual(str(child), '/parent/dir/child.txt')


if __name__ == '__main__':
    unittest.main()

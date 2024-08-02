import unittest
from pathlib import Path


class TestDirTraversal(unittest.TestCase):

    def test_iterdir(self):
        p: Path = Path('/tmp/')
        for child in p.iterdir():
            if child.is_file():
                print(f'File: {child}')
            else:
                print(f'Dir: {child}')

    def test_glob(self):
        p: Path = Path('/tmp/')
        for child in p.glob("*.tmp"):
            if child.is_file():
                print(f'File: {child}')
            else:
                print(f'Dir: {child}')


if __name__ == "__main__":
    unittest.main()

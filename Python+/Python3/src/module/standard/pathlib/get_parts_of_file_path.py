import unittest
from pathlib import Path


class TestGetPartsOfPath(unittest.TestCase):
    def test_pathlib_functions(self):
        p: Path = Path('/tmp/work/data.txt')
        parent_dir = p.parent
        parent_dir_name = p.parent.name
        file_name = p.name
        base_name = p.stem
        extension = p.suffix

        self.assertEqual(str(parent_dir), '/tmp/work')
        self.assertEqual(parent_dir_name, 'work')
        self.assertEqual(file_name, 'data.txt')
        self.assertEqual(base_name, 'data')
        self.assertEqual(extension, '.txt')


if __name__ == '__main__':
    unittest.main()

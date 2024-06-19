import os
import shutil
import tempfile

import unittest


class TestRemoveTree(unittest.TestCase):

    def test_remove_tree(self):
        tmp_dir: str = tempfile.mkdtemp()

        tmp_file: str = os.path.join(tmp_dir, 'file1.txt')
        with open(tmp_file, 'w') as f:
            f.write('abc')
        self.assertTrue(os.path.isdir(tmp_dir))
        self.assertTrue(os.path.isfile(tmp_file))

        shutil.rmtree(tmp_dir)
        self.assertFalse(os.path.isfile(tmp_file))
        self.assertFalse(os.path.isdir(tmp_dir))


if __name__ == '__main__':
    unittest.main()

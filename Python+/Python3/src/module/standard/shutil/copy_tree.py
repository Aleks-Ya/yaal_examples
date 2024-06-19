import os
import shutil
import tempfile

import unittest


class TestCopyTree(unittest.TestCase):

    def test_copy_tree(self):
        src_dir: str = tempfile.mkdtemp()

        filename: str = 'file1.txt'
        src_file: str = os.path.join(src_dir, filename)
        exp_content: str = 'abc'
        with open(src_file, 'w') as f:
            f.write(exp_content)

        dest_dir: str = tempfile.mkdtemp()
        os.removedirs(dest_dir)

        shutil.copytree(src_dir, dest_dir)

        dest_file: str = os.path.join(dest_dir, filename)
        with open(dest_file) as f:
            act_content = f.read()
        self.assertEqual(exp_content, act_content)


if __name__ == '__main__':
    unittest.main()

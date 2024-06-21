import os
import shutil
import tempfile

import unittest


class TestCopyFile(unittest.TestCase):

    def test_copy_file_to_file(self):
        _, source_file = tempfile.mkstemp(".tmp")
        exp_content: str = 'abc'
        with open(source_file, 'w') as f:
            f.write(exp_content)

        _, dest_file = tempfile.mkstemp(".tmp")
        os.remove(dest_file)

        shutil.copyfile(source_file, dest_file)

        with open(dest_file) as f:
            act_content = f.read()
        self.assertEqual(exp_content, act_content)

    def test_copy_file_to_dir(self):
        _, source_file = tempfile.mkstemp(".tmp")
        exp_content: str = 'abc'
        with open(source_file, 'w') as f:
            f.write(exp_content)

        dest_dir = tempfile.mkdtemp()

        shutil.copy(source_file, dest_dir)

        dest_file: str = os.path.join(dest_dir, os.path.basename(source_file))
        with open(dest_file) as f:
            act_content = f.read()
        self.assertEqual(exp_content, act_content)


if __name__ == '__main__':
    unittest.main()

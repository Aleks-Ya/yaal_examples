import os
import shutil
import tempfile

import unittest


class TestCopyFile(unittest.TestCase):

    def test_copy_file(self):
        _, source_file = tempfile.mkstemp()
        exp_content: str = 'abc'
        with open(source_file, 'w') as f:
            f.write(exp_content)

        _, dest_file = tempfile.mkstemp()
        os.remove(dest_file)

        shutil.copyfile(source_file, dest_file)

        with open(dest_file) as f:
            act_content = f.read()
        self.assertEqual(exp_content, act_content)


if __name__ == '__main__':
    unittest.main()

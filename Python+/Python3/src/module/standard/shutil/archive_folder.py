import os
import shutil
import tempfile

import unittest


class MakeArchiveTestCase(unittest.TestCase):

    def test_make_archive(self):
        src_dir: str = tempfile.mkdtemp()
        file_1: str = os.path.join(src_dir, 'file1.txt')
        file_2: str = os.path.join(src_dir, 'file2.txt')

        exp_content_1: str = 'abc'
        with open(file_1, 'w') as f:
            f.write(exp_content_1)

        exp_content_2: str = 'xyz'
        with open(file_2, 'w') as f:
            f.write(exp_content_2)

        dest_zip: str = tempfile.mktemp()
        output_zip: str = shutil.make_archive(dest_zip, 'zip', src_dir)

        extract_dir: str = tempfile.mkdtemp()
        shutil.unpack_archive(output_zip, extract_dir, 'zip')

        with open(os.path.join(extract_dir, 'file1.txt'), 'r') as f:
            act_content_1 = f.read()

        self.assertEqual(exp_content_1, act_content_1)


if __name__ == '__main__':
    unittest.main()

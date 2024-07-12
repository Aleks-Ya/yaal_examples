import os

import unittest


class TestGetCurrentFileName(unittest.TestCase):
    def setUp(self):
        self.full_path = __file__
        self.file_with_extension = os.path.basename(self.full_path)
        self.file_without_extension = os.path.splitext(self.file_with_extension)[0]
        self.dir_name = os.path.dirname(self.full_path)

    def test_full_path(self):
        self.assertTrue(str(self.full_path).endswith('Python+/Python3/src/core/clazz/simple/get_current_file_name.py'))

    def test_file_with_extension(self):
        self.assertEqual(self.file_with_extension, 'get_current_file_name.py')

    def test_file_without_extension(self):
        self.assertEqual(self.file_without_extension, 'get_current_file_name')

    def test_dir_name(self):
        self.assertTrue(str(self.dir_name).endswith('Python+/Python3/src/core/clazz/simple'))


if __name__ == '__main__':
    unittest.main()

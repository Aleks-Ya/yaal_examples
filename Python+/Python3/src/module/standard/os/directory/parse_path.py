# Get directory name from full file name
import os
import unittest


class ParsePathTestCase(unittest.TestCase):

    def test_dirname_of_file(self):
        file_name: str = '/tmp/work/data.txt'
        dir_name: str = os.path.dirname(file_name)
        self.assertEqual('/tmp/work', dir_name)

    def test_basename(self):
        file_name: str = '/tmp/work/data.txt'
        dir_name: str = os.path.dirname(file_name)
        self.assertEqual('/tmp/work', dir_name)
        base_name: str = os.path.basename(dir_name)
        self.assertEqual('work', base_name)

    def test_dirname_of_dir(self):
        file_name: str = '/tmp/work/data.txt'
        dir_name: str = os.path.dirname(file_name)
        self.assertEqual('/tmp/work', dir_name)
        top_dir_name: str = os.path.dirname(dir_name)
        self.assertEqual('/tmp', top_dir_name)


if __name__ == '__main__':
    unittest.main()

import os
import tempfile

import unittest
from tempfile import TemporaryDirectory


class TestTempFile(unittest.TestCase):

    def test_mkdtemp(self):
        full_name: str = tempfile.mkdtemp()
        self.assertTrue(os.path.isdir(full_name))
        self.assertTrue(os.path.exists(full_name))
        os.removedirs(full_name)

    def test_temporary_directory(self):
        with TemporaryDirectory() as td:
            self.assertTrue(os.path.isdir(td))
            self.assertTrue(os.path.exists(td))
            os.removedirs(td)


if __name__ == '__main__':
    unittest.main()

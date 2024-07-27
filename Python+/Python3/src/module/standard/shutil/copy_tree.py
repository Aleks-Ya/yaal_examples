import os
import shutil
import tempfile

import unittest
from pathlib import Path
from typing import Callable, Any


class TestCopyTree(unittest.TestCase):
    def setUp(self):
        self.src_dir: Path = Path(tempfile.mkdtemp())
        self.dest_dir: Path = Path(tempfile.mkdtemp())

    def test_copy_tree(self):
        filename: str = 'file1.txt'
        src_file: Path = self.src_dir.joinpath(filename)
        exp_content: str = 'abc'
        src_file.write_text(exp_content)
        os.removedirs(self.dest_dir)
        dest_file: Path = self.dest_dir.joinpath(filename)
        shutil.copytree(self.src_dir, self.dest_dir)
        act_content = dest_file.read_text()
        self.assertEqual(exp_content, act_content)

    def test_copy_tree_ignore(self):
        filename_included: str = 'file.txt'
        filename_ignored: str = 'file.tmp'
        src_file_included: Path = self.src_dir.joinpath(filename_included)
        src_file_ignored: Path = self.src_dir.joinpath(filename_ignored)
        src_file_included.touch()
        src_file_ignored.touch()
        os.removedirs(self.dest_dir)
        dest_file_included: Path = self.dest_dir.joinpath(filename_included)
        dest_file_ignored: Path = self.dest_dir.joinpath(filename_ignored)

        ignore_patterns: Callable[[Any, list[str]], set[str]] = shutil.ignore_patterns("*.tmp")
        shutil.copytree(self.src_dir, self.dest_dir, ignore=ignore_patterns)

        self.assertTrue(dest_file_included.exists())
        self.assertFalse(dest_file_ignored.exists())


if __name__ == '__main__':
    unittest.main()

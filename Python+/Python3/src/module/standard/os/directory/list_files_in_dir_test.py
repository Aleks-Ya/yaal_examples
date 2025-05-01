import os
from typing import List


def test_list_files_in_dir():
    files: List[str] = os.listdir('.')
    assert 'list_files_in_dir_test.py' in files

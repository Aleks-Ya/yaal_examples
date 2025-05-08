import os
from typing import List


def test_list_files_in_dir():
    current_dir: str = os.path.dirname(__file__)
    files: List[str] = os.listdir(current_dir)
    assert 'list_files_in_dir_test.py' in files

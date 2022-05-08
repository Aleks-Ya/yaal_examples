# List all files in a directory
import os
from typing import List

files: List[str] = os.listdir('.')
assert 'list_files_in_dir.py' in files

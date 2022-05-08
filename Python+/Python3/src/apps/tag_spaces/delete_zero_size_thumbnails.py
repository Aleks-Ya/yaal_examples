# Find and delete broken (sero-length) thumbnails in TagSpaces location directory.
# Run: python delete_zero_size_thumbnails.py /home/aleks/yandex-disk/video/Wakeboard
import os
import sys
from typing import Any, List


def on_error(error: Any):
    raise error


root_dir: str = sys.argv[1]
ts_dir_name: str = ".ts"
ts_dirs: List[str] = [tup[0] for tup in os.walk(root_dir, onerror=on_error)
                      if tup[0] == ts_dir_name or tup[0].endswith(os.sep + ts_dir_name)]
all_empty_files: List[str] = []
for ts_dir in ts_dirs:
    files: List[str] = os.listdir(ts_dir)
    full_paths: List[str] = [os.path.join(ts_dir, file) for file in files]
    empty_files: List[str] = [file for file in full_paths if os.path.getsize(file) == 0]
    all_empty_files += empty_files
print(f'Empty files: {all_empty_files}')
print(f'Empty files number: {len(all_empty_files)}')
for empty_file in all_empty_files:
    print(f"Deleting '{empty_file}'...", end='')
    os.remove(empty_file)
    print(' OK')

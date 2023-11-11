# Get directory name from full file name
import os

file_name: str = '/tmp/work/data.txt'
dir_name: str = os.path.dirname(file_name)
assert dir_name == '/tmp/work'
last_dir_name: str = os.path.basename(dir_name)
assert last_dir_name == 'work'

# Get directory name from full file name
import os

file_name = '/tmp/work/data.txt'
dir_name = os.path.dirname(file_name)
assert dir_name == '/tmp/work'
last_dir_name = os.path.splitext(dir_name)
assert last_dir_name == '/tmp/work'

import os

# Extract directory name from absolute path to file
absolute_path = '/tmp/abc/data.txt'
dir_name = os.path.dirname(absolute_path)
assert dir_name == '/tmp/abc'

# Extract file name from absolute path to file
absolute_path = '/tmp/abc/data.txt'
dir_name = os.path.basename(absolute_path)
assert dir_name == 'data.txt'

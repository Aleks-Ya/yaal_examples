import os

# Extract directory name from absolute path to file
absolute_path = '/tmp/abc/data.txt'
dir_name = os.path.dirname(absolute_path)
assert dir_name == '/tmp/abc'

# Extract file name from absolute path to file
absolute_path = '/tmp/abc/data.txt'
dir_name = os.path.basename(absolute_path)
assert dir_name == 'data.txt'

# Extract parent dir name
absolute_path = '/tmp/abc/data.txt'
dir_name = os.path.basename(os.path.dirname(absolute_path))
assert dir_name == 'abc'

# Join directory path and filename
dir_name = '/tmp/abc'
file_name = 'data.txt'
full_name = os.path.join(dir_name, file_name)
assert full_name == '/tmp/abc/data.txt'

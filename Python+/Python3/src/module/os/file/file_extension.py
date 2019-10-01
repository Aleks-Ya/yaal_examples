import os

# Get file extension
absolute_path = '/tmp/abc/data.txt'
split = os.path.splitext(absolute_path)
file_name = split[0]
file_extension = split[1]
assert file_name == '/tmp/abc/data'
assert file_extension == '.txt'

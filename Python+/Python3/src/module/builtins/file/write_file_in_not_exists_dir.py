# You have to create full path to directory before open file for writing.
import tempfile
import os

tmp_dir = tempfile.mkdtemp()
not_exists_dir = tmp_dir + "/not_exists_1/not_exists_2"
file_full_name = not_exists_dir + "/data.txt"

os.makedirs(not_exists_dir)

with open(file_full_name, 'w') as f:
    exp_content = 'abc'
    f.write(exp_content)

with open(file_full_name) as f:
    act_content = f.readline()

os.remove(file_full_name)
os.removedirs(not_exists_dir)  # removes tmp_dir and all empty directories recursive

assert act_content == exp_content

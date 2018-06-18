# Create a directory hierarchy
import os
import tempfile
from csv import excel

tmp_dir = tempfile.mkdtemp()
not_exist_path = tmp_dir + "/d1/d2"

print("Full name: " + not_exist_path)

assert not os.path.isdir(not_exist_path)

os.makedirs(not_exist_path)
os.makedirs(not_exist_path, exist_ok=True)

assert os.path.isdir(not_exist_path)

os.removedirs(not_exist_path)

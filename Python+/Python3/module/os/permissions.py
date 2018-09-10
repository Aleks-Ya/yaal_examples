# Set permission "write for others"
import os
import tempfile

path = tempfile.mkdtemp() + "/my_dir"
os.makedirs(path)

st = os.stat(path)
assert oct(st.st_mode) == '0o40775'

os.chmod(path, 0o666)

st = os.stat(path)
assert oct(st.st_mode) == '0o40666'

os.removedirs(path)

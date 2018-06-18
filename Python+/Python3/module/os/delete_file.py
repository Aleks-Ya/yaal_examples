import os
import tempfile

f = tempfile.mkstemp()
full_name = f[1]
print("Full name: " + full_name)

assert os.path.isfile(full_name)

os.remove(full_name)

assert not os.path.isfile(full_name)

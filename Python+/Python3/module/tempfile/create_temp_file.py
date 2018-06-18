import tempfile
import os

f = tempfile.mkstemp()
full_name = f[1]
print("Full name: " + full_name)

os.remove(full_name)

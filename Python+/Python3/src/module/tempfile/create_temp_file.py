import tempfile
import os

f: str = tempfile.mkstemp()
full_name: str = f[1]
print("Full name: " + full_name)

os.remove(full_name)

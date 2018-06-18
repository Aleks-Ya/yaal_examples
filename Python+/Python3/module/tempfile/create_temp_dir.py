import tempfile
import os

full_name = tempfile.mkdtemp()
print("Full name: " + full_name)

os.removedirs(full_name)

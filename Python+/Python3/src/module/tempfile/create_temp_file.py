import os
import tempfile

file_descriptor, full_name = tempfile.mkstemp()
print(f"File descriptor: {file_descriptor}, File name: {full_name}")

os.remove(full_name)

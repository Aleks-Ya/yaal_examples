import os
import shutil
import tempfile

_, source_file = tempfile.mkstemp()
exp_content = 'abc'
with open(source_file, 'w') as f:
    f.write(exp_content)

_, dest_file = tempfile.mkstemp()
os.remove(dest_file)

shutil.copyfile(source_file, dest_file)

with open(dest_file) as f:
    act_content = f.read()
assert act_content == exp_content

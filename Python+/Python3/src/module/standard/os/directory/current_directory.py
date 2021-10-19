# Current directory
import os

# Get current dir
current_directory = os.getcwd()
print(f'current_directory: {current_directory}')

# Set current dir
old_current_dir = os.getcwd()
new_current_dir = '/tmp'
os.chdir(new_current_dir)
assert os.getcwd() == new_current_dir

file = 'current_directory.txt'
with open(file, 'w') as f:
    exp_content = f'current dir is {os.getcwd()}'
    f.write(exp_content)

os.chdir(old_current_dir)

with open(os.path.join(new_current_dir, file)) as f:
    act_content = f.readline()
assert act_content == exp_content

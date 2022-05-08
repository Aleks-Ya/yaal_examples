import os

# Join directory and file names
current_dir: str = os.getcwd()
file_name: str = 'join_paths.py'
full_file_path: str = os.path.join(current_dir, file_name)
print(f'Full path: {full_file_path}')
assert (full_file_path.endswith('/os/directory/join_paths.py'))

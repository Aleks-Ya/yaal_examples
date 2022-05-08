import os

folder: str = os.getcwd()
file: str = 'join_paths.py'
full_path: str = os.path.join(folder, file)

full_path_by_sep: str = folder + os.sep + file

assert (full_path == full_path_by_sep)

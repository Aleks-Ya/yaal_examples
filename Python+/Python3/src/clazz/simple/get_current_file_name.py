# Get filename of current Python file
import os

full_path = __file__
file = os.path.basename(full_path)
assert str(full_path).endswith('get_current_file_name.py')
assert file == 'get_current_file_name.py'

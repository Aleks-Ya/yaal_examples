# Get filename of current Python file
import os

full_path = __file__
file_with_extension = os.path.basename(full_path)
file_without_extension = os.path.splitext(file_with_extension)[0]
dir = dir_name = os.path.dirname(full_path)
assert str(full_path).endswith('get_current_file_name.py')
assert file_with_extension == 'get_current_file_name.py'
assert file_without_extension == 'get_current_file_name'
assert str(dir).endswith('src/clazz/simple')

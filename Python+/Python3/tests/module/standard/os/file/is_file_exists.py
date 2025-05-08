# Check does file exist

import os

# File exists
assert os.path.isfile('is_file_exists.py')
assert not os.path.isfile('not_exist.py')

# File or directory exists
assert os.path.exists('is_file_exists.py')
assert not os.path.exists('../not_exists')

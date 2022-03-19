# Check does directory exist

import os

# Directory exists
assert os.path.isdir('.')
assert not os.path.isdir('../not_exists')

# File or directory exists
assert os.path.exists('is_file_exists.py')
assert not os.path.exists('../not_exists')

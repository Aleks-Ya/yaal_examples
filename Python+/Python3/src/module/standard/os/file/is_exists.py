# Check does file or directory exist

import os

# File exists
assert os.path.isfile('is_exists.py')
assert not os.path.isfile('not_exist.py')

# Directory exists
assert os.path.isdir('')
assert not os.path.isdir('../not_exists')

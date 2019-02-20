# Absolute and relative path
import os

# Relative to absolute
relative = '../file/absolute_relative_path.py'
absolute = os.path.abspath(relative)
print(absolute)
assert absolute == __file__

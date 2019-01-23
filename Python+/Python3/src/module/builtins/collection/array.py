# Using arrays

# One-dimensional array
a = ['a', 'b', 'c']
assert a[0] == 'a'

# Multidimensional array
x = {(0, 0, 0): 11,
     (1, 0, 0): 21,
     (0, 1, 1): 111}
assert x[0, 0, 0] == 11
assert x[1, 0, 0] == 21
assert x[0, 1, 1] == 111

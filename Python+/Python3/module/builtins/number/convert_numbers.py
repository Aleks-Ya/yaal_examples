# Convert numbers

# float to int
i = int(123.4)
assert i == 123

# Not a number exception
success = False
try:
    n = 'abc'
    int(n)
except ValueError as e:
    success = True
assert success

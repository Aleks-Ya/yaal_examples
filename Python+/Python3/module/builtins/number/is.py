# Checking number types

# Check float is int
f = 123.4
res = f.is_integer()
assert not res

# Check variable contains a float
f = 1.1
is_float = isinstance(f, float)
assert is_float

i = 1
is_float = isinstance(i, float)
assert not is_float

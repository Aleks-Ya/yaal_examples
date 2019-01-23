# Boolean data type

# Literals
assert True
assert not False

a = True
assert a

# Is variable value is bool
a = True
assert type(a) == bool
b = 'True'
assert type(b) != bool
c = None
assert type(c) != bool
# Convert string to number

# To int
a = int('5')
assert a == 5

# To boolean
b = bool('True')
assert b

b = bool('')
assert not b


def to_bool(s: str):
    return s.lower() in ['true', '1', 't', 'y', 'yes']


b = to_bool('True')
assert b
b = to_bool('False')
assert not b

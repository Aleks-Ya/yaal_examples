# Create dictionary

# From literals
a = dict(one=1, two=2, three=3)
b = {'one': 1, 'two': 2, 'three': 3}
c = dict(zip(['one', 'two', 'three'], [1, 2, 3]))
d = dict([('two', 2), ('one', 1), ('three', 3)])
e = dict({'three': 3, 'one': 1, 'two': 2})
assert a == b == c == d == e

# From list of tuples
strings = ['a', 'bb', 'ccc']
d = dict([(s, len(s)) for s in strings])
assert d == {'a': 1, 'bb': 2, 'ccc': 3}

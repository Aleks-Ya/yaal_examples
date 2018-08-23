# Map (Dictionary)

# Create dictionary
a = dict(one=1, two=2, three=3)
b = {'one': 1, 'two': 2, 'three': 3}
c = dict(zip(['one', 'two', 'three'], [1, 2, 3]))
d = dict([('two', 2), ('one', 1), ('three', 3)])
e = dict({'three': 3, 'one': 1, 'two': 2})
assert a == b == c == d == e

# Add item to an empty dictionary
empty_dict = dict()
empty_dict['one'] = 1
empty_dict['two'] = 2
assert empty_dict == dict(one=1, two=2)

# Add item to exists dictionary
n = dict(one=1, two=2)
n['three'] = 3
assert n == dict(one=1, two=2, three=3)

# Join dictionaries
a = dict(one=1, two=2)
b = dict(one=11, three=3)
join = a.copy()
join.update(b)
assert join == dict(one=11, two=2, three=3)

# Size
d = dict(one=1, two=2)
assert len(d) == 2

# Get element by key
d = dict(one=1, two=2)
assert d['one'] == 1
assert d.get('one') == 1

# Contains key?
d = dict(one=1, two=2)
assert 'one' in d
assert 'three' not in d

# Iterate keys
d = {'a': 1, 'b': 2, 'c': 3}
res = ''
for key in d:
    value = d[key]
    res += key
    res += str(value)
assert res == 'a1b2c3'

# Iterate keys and values
d = {'a': 1, 'b': 2, 'c': 3}
res = ''
for key, value in d.items():
    res += key
    res += str(value)
assert res == 'a1b2c3'


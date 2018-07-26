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
n = dict(one=1, two=2)
assert len(n) == 2

# Get element by key
n = dict(one=1, two=2)
assert n['one'] == 1
assert n.get('one') == 1

# Contains key?
n = dict(one=1, two=2)
assert 'one' in n
assert not 'three' in n

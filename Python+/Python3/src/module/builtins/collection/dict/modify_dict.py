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

# Remove element from dict
d = {'a': 1, 'b': 2}
del d['a']
assert d == {'b': 2}

# Filter a dict 1
e = {'a': 1, 'b': -2, 'c': 3}
filtered = {k: v for k, v in e.items() if v > 0}
assert filtered == {'a': 1, 'c': 3}

# Filter a dict 2
e = {'a': 1, 'b': -2, 'c': 3}
filtered = dict(filter(lambda val: val[1] > 0, e.items()))
assert filtered == {'a': 1, 'c': 3}

# Dict to a key list
d = {'a': 1, 'b': 2, 'c': 3}
key_list = list(d.keys())
assert key_list == ['a', 'b', 'c']

# Dict to a value list
d = {'a': 1, 'b': 2, 'c': 3}
key_list = list(d.values())
assert key_list == [1, 2, 3]

# List to dict
l = ['a', 'bb', 'ccc']
d = {element: len(element) for element in l}
assert d == {'a': 1, 'bb': 2, 'ccc': 3}

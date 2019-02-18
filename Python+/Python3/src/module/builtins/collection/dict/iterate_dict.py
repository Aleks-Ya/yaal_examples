# Iterate keys
d = {'a': 1, 'b': 2, 'c': 3}
res = ''
for key in d:
    value = d[key]
    res += key
    res += str(value)
assert res == 'a1b2c3'

# Iterate values
d = {'a': 1, 'b': 2, 'c': 3}
res = ''
for value in d.values():
    res += str(value)
assert res == '123'

# Iterate values (one line)
d = {'a': 1, 'b': 2, 'c': 3}
res = {value: 'a' for value in d.values()}
assert res == {1: 'a', 2: 'a', 3: 'a'}

# Iterate keys and values
d = {'a': 1, 'b': 2, 'c': 3}
res = ''
for key, value in d.items():
    res += key
    res += str(value)
assert res == 'a1b2c3'


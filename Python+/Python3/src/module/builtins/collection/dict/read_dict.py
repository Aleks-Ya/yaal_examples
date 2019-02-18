# Get element by key
d = dict(one=1, two=2)
assert d['one'] == 1
assert d.get('one') == 1

# Contains key?
d = dict(one=1, two=2)
assert 'one' in d
assert 'three' not in d

# Size
d = dict(one=1, two=2)
assert len(d) == 2

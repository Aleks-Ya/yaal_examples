# Get existing element by key
d = dict(one=1, two=2)
assert d['one'] == 1
assert d.get('one') == 1

# Get absent element by key
d = dict()
assert d.get('absent') is None
try:
    v = d['absent']
    raise AssertionError('Should not reach this line')
except KeyError as ex:
    assert ex.args[0] == "absent"

# Contains key?
d = dict(one=1, two=2)
assert 'one' in d
assert 'three' not in d

# Size
d = dict(one=1, two=2)
assert len(d) == 2

# Compare strings

# length
assert len('abc') == 3

# Is a string contains a substring
s = 'abc'
assert 'b' in s
assert 'z' not in s

# is empty
s = ''
is_empty = not s
assert is_empty

# is not empty
s = 'a'
assert s

# Is not None and not empty
s = None
if s:
    assert False
else:
    assert True

s = ''
if s:
    assert False
else:
    assert True

s = 'a'
if s:
    assert True
else:
    assert False

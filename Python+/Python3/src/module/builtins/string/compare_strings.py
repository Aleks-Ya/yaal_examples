# Compare strings

# length
assert len('abc') == 3

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

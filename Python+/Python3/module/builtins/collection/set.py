# Using sets

# Create a set
s = {1, 2}
assert len(s) == 2

# Add element to set
s = {1, 2}
s.add(3)
assert s == {1, 2, 3}

# No duplicates in set
s = {1, 2, 1}
assert len(s) == 2

# Not supported: get element by index

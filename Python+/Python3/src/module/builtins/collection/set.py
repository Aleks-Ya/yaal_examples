# Using sets

# Create an empty set
s: set[int] = set()

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

# Sort a set
s = {3, 2, 1}
sorted(s)
assert s == {1, 2, 3}

# Join sets by update
s1: set[int] = {10, 11}
s2: set[int] = {1, 2}
s1.update(s2)
assert s1 == {10, 11, 1, 2}

# Join sets by union
s1 = {10, 11}
s2 = {1, 2}
s3: set[int] = s1.union(s2)
assert s3 == {10, 11, 1, 2}

# Join sets by |=
s1 = {10, 11}
s2 = {1, 2}
s1 |= s2
assert s1 == {10, 11, 1, 2}

# Using tuples

# Create an empty tuple
t = ()
assert len(t) == 0

# Create a single-value tuple (need comma)
t = (50,)
assert t[0] == 50

# Create a multi value tuple
t = ('a', 1, 'b', 2)
assert t[0] == 'a'
assert t[1] == 1
assert t[2] == 'b'
assert t[3] == 2

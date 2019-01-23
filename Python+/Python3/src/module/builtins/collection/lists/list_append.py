# Add element to a list

# Concatenation
c = [1, 2] + [3, 4]
assert c == [1, 2, 3, 4]

# Append a list
c = [1, 2]
c += [3, 4]
assert c == [1, 2, 3, 4]

# Append an element
# noinspection PyListCreation
app = [1, 2]
app.append(3)
assert app == [1, 2, 3]

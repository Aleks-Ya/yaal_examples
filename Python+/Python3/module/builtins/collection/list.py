# List

# Create a single element list
single_element_list = [25]
assert single_element_list[0] == 25

# Create a multi element list
squares = [1, 4, 9, 16, 25]

# length
assert len([1, 2]) == 2

# get element by index
assert squares[1] == 4
assert squares[-2] == 16

# slicing
assert squares[1:3] == [4, 9]
assert squares[:3] == [1, 4, 9]
assert squares[3:] == [16, 25]
assert squares[-2:] == [16, 25]
assert squares[:-2] == [1, 4, 9]

# copy
copy = squares[:]
assert copy == squares

# concatenation
c = [1, 2] + [3, 4]
assert c == [1, 2, 3, 4]

# replace
lst = [1, 3, 3]
lst[1] = 2
assert lst == [1, 2, 3]

# append
# noinspection PyListCreation
app = [1, 2]
app.append(3)
assert app == [1, 2, 3]

# nested lists
lst = [[1, 2], ['a', 'b']]
assert len(lst) == 2

# is empty
lst = []
is_empty = not lst
assert is_empty

# is not empty
lst = [1]
if lst:
    is_empty = False
else:
    is_empty = True
assert is_empty is False

# iterate list
lst = ['a', 'b', 'c']
result = ''
for char in lst:
    result = result + char
assert result == 'abc'

# iterate list (with indexes)
lst = ['a', 'b', 'c']
result = ''
for i, char in enumerate(lst):
    result = result + str(i) + char
assert result == '0a1b2c'

# find element index by object
lst = ['a', 'b', 'c']
assert lst.index('b') == 1

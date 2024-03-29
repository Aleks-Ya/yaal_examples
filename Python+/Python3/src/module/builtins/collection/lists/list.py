# List

# Create an empty list
e = []
assert len(e) == 0

# Create a single element list
single_element_list = [25]
assert single_element_list[0] == 25

# Create a multi element list
squares = [1, 4, 9, 16, 25]

# length
assert len([1, 2]) == 2

# Does contain an element?
symbols = ['a', 'b', 'c']
contains_b = 'b' in symbols
assert contains_b
contains_z = 'z' in symbols
assert not contains_z

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

# replace
lst = [1, 3, 3]
lst[1] = 2
assert lst == [1, 2, 3]

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

# transform list elements
lst = ['a', 'b', 'c']
result_map = map(lambda symbol: symbol.upper(), lst)
result_list = list(result_map)
assert result_list == ['A', 'B', 'C']

# "list comprehension" is iterating a list in braces "[]"

numbers = [1, 2, 3]

# Change value
list_changed = [num * 2 for num in numbers]
assert list_changed == [2, 4, 6]

# Filter values
list_filtered = [num for num in numbers if num > 1]
assert list_filtered == [2, 3]

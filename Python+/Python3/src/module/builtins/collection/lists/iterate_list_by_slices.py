# Iterate a list by slices

numbers = [1, 2, 3, 4, 5]
c = 2
list_of_slices = [numbers[i:i + c] for i in range(0, len(numbers), c)]
assert list_of_slices == [[1, 2], [3, 4], [5]]

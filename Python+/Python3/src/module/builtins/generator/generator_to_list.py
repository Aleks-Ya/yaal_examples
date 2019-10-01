# Convert generator to list
from typing import List

# By list()
generator = range(3)
nums: List[int] = list(generator)
assert nums == [0, 1, 2]

# By list comprehension
generator = range(3)
nums: List[int] = [x for x in generator]
assert nums == [0, 1, 2]

# By list comprehension (with filtering)
generator = range(3)
nums: List[int] = [x for x in generator if x > 1]
assert nums == [2]

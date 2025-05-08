# Define type of loop variable

from typing import List

lst: List[int] = [1, 2, 3]
result: int = 0
num: int
for num in lst:
    result = result + num
assert result == 6

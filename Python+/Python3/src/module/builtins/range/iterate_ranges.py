# Using ranges
from typing import List

r: range = range(2)
res: List[int] = []
for num in r:
    res.append(num)
assert res == [0, 1]

r: range = range(1, 5)
res: List[int] = []
[res.append(num) for num in r]
assert res == [1, 2, 3, 4]

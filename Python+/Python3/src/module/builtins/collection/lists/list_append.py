# Add element to a list
from typing import List

# Concatenation
c: List[int] = [1, 2] + [3, 4]
assert c == [1, 2, 3, 4]

# Append a list
c: List[int] = [1, 2]
old_id: id = id(c)
c += [3, 4]
new_id: id = id(c)
assert c == [1, 2, 3, 4]
assert new_id == old_id  # old list was appended instead of creating new instance

# Append an element
# noinspection PyListCreation
app: List[int] = [1, 2]
app.append(3)
assert app == [1, 2, 3]

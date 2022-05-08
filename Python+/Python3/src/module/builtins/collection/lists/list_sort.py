# Sort a list
from typing import List

l: List[str] = ['a', 'z', 'b', 't']

# Sort ascending
l.sort()
assert l == ['a', 'b', 't', 'z']

# Sort descending
l.sort(reverse=True)
assert l == ['z', 't', 'b', 'a']

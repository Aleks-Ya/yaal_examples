# Use "for" cycle

# Break
lst = ['a', 'b', 'c']
char = None
for char in lst:
    if char == 'b':
        break
assert char == 'b'

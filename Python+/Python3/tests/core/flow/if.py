# Using "if" statement

# Full form
x = 1
if x < 0:
    x = 0
    print('Negative changed to zero')
elif x == 0:
    print('Zero')
elif x == 1:
    print('Single')
else:
    print('More')

# One line
a = "a" if 1 > 0 else "b"
assert a == "a"

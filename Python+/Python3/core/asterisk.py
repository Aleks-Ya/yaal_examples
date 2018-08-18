# Using asterisk function (*) in front of variables

# Asterisk as a function
t = (1, 2, 3)
print(t)
print(*t)

# Single asterisk - any number of arguments AS A TUPLE
a = 1
b = 2
c = 3


def print_args(*args):
    print(args)


print_args(a, b, c)

# Double asterisk - any number of arguments AS A DICTIONARY
a = 1
b = 2
c = 3


def print_args(**args):
    print(args)


print_args(a=7, b=8, c=9)

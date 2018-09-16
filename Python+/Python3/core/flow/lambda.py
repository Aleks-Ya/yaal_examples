# Use lambdas

# No args lambda
lm = lambda: 1
assert lm() == 1

# Assign lambda to a variable
lm = lambda a, b: a + b
assert lm(1, 2) == 3


# Multi line lambda
def func(a, b):
    k = a * 2
    n = b * 3
    return k + n


lm = lambda a, b: func(a, b)
assert lm(1, 2) == 8

# Sort elements of list with lambda
pairs = [(1, 'one'), (2, 'two'), (3, 'three'), (4, 'four')]
pairs.sort(key=lambda pair: pair[1])
assert str(pairs) == "[(4, 'four'), (1, 'one'), (3, 'three'), (2, 'two')]"


# Execute a lambda
def execute(func, *arg):
    return func(*arg)


assert execute(lambda a, b: a + b, 2, 3) == 5

# Catch exception from lambda
exc = None
try:
    lm = lambda: exec("raise RuntimeError('Hi')")
    lm()
except RuntimeError as e:
    exc = e
    pass
assert str(exc) == 'Hi'

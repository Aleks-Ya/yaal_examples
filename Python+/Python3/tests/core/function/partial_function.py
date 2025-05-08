from functools import partial


def my_function(a, b, c):
    return a + b + c


partial_func = partial(my_function, 1, 2)
result = partial_func(3)  # Equivalent to my_function(1, 2, 3)
assert result == 6

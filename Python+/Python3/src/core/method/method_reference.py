# Use references to methods


def plus_one_func(a: int) -> int:
    return a + 1


def execute_func(func, *args):
    return func(*args)


assert execute_func(plus_one_func, 2) == 3

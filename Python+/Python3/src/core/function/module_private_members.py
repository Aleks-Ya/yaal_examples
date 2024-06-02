__private_var = 1


def __private_method():
    return __private_var


assert __private_var == 1
assert __private_method() == 1
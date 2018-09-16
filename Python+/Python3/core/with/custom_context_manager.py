# Custom with processor
# NOT FINISHED!!!
from contextlib import contextmanager


@contextmanager
def var_a(b):
    print("set var")
    a = 1
    try:
        print("Try 1: ", a)
        try:
            print("Try 2: ", a)
            raise Exception
            # yield a + b
        except Exception as e:
            print("Except 2: ", a)
            yield a + 2
    finally:
        print("Finally 1: ", a)


with var_a(3) as a:
    print("with ", a)




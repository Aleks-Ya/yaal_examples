# Lazy variables


class LazyWrapper(object):
    def __init__(self, func):
        self.func = func
        self.value = None

    def __call__(self):
        if self.value is None:
            self.value = self.func()
        return self.value


def initializer(number):
    return number * 2


def test_lazy_wrapper():
    lazy_wrapper = LazyWrapper(lambda: initializer(3))
    value = lazy_wrapper()
    assert value == 6

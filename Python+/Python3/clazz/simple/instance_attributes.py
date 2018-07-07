# Instance attributes


# Public attributes
class MyClass:
    def __init__(self):
        self.i = 12345

    def f(self):
        return 'hello world ' + str(self.i)


x = MyClass()
assert x.i == 12345
assert x.f() == 'hello world 12345'


# Private variables
class MyClass:
    def __init__(self):
        self._i = 12345


x = MyClass()
# private variable is accessible, but should be used
assert x._i == 12345

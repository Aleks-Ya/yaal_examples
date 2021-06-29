# Get class of an object


class MyClass:
    """my class"""


a = MyClass()
assert eval('MyClass') == type(a)
assert globals()['MyClass'] == type(a)

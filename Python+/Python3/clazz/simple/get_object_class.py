# Get class of an object


class MyClass:
    """my class"""


a = MyClass()
assert a.__class__.__name__ == 'MyClass'

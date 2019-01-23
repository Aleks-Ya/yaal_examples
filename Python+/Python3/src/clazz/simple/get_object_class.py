# Get class of an object


class MyClass:
    """my class"""


a = MyClass()
assert type(a).__name__ == 'MyClass'

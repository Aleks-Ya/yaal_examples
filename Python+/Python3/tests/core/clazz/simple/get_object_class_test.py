# Get class of an object


class MyClass:
    """my class"""


def test_my_class_type():
    a: MyClass = MyClass()
    assert type(a).__name__ == 'MyClass'

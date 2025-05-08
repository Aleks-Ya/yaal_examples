# Test class of an object


class MyClass:
    """my class"""


def test_myclass_type():
    a: MyClass = MyClass()
    assert eval('MyClass') == type(a)
    assert globals()['MyClass'] == type(a)

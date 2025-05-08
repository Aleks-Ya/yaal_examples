# Class inheritance


class ParentClass:
    def __init__(self):
        self.data1 = 'hello'


class DerivedClass(ParentClass):
    def __init__(self):
        super().__init__()
        self.data2 = 'hi'


def test_inheritance():
    x = DerivedClass()
    assert x.data1 == 'hello'
    assert x.data2 == 'hi'

    # Is instance of
    assert isinstance(x, ParentClass)
    assert isinstance(x, DerivedClass)

    # Is subclass of
    assert issubclass(DerivedClass, ParentClass)

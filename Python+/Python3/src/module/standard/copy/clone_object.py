import copy


class NestedClass:
    def __init__(self):
        self.number = 1


class MyClass:
    def __init__(self):
        self.text = 'xyz'
        self.nested = NestedClass()


nested: NestedClass = NestedClass()
nested.number = 2

origin: MyClass = MyClass()
origin.text = 'abc'
origin.nested = nested
assert origin.text == 'abc'
assert origin.nested == nested
assert origin.nested.number == 2


def test_shallow_copy():
    shallow: MyClass = copy.copy(origin)
    assert origin != shallow
    assert shallow.text == 'abc'
    assert shallow.nested == nested
    assert shallow.nested.number == 2


def test_deep_copy():
    deep: MyClass = copy.deepcopy(origin)
    assert origin != deep
    assert deep.text == 'abc'
    assert deep.nested != nested
    assert deep.nested.number == 2

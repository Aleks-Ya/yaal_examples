# Clone objects

import copy


class NestedClass:
    def __init__(self):
        self.number = 1


nested = NestedClass()
nested.number = 2


class MyClass:
    def __init__(self):
        self.text = 'xyz'
        self.nested = NestedClass()


origin = MyClass()
origin.text = 'abc'
origin.nested = nested
assert origin.text == 'abc'
assert origin.nested == nested
assert origin.nested.number == 2

# Shallow copy (use exists nested objects)
shallow = copy.copy(origin)
assert origin != shallow
assert shallow.text == 'abc'
assert shallow.nested == nested
assert shallow.nested.number == 2

# Deep copy (clones nested objects)
deep = copy.deepcopy(origin)
assert origin != deep
assert deep.text == 'abc'
assert deep.nested != nested
assert deep.nested.number == 2

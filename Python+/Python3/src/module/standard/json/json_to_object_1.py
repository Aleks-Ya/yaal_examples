# Convert JSON to an object
import json


class Foo(object):
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __repr__(self):
        return f'Foo-{self.x}-{self.y}'


content = '{"x": 5, "y": 10}'
f = json.loads(content)
foo = Foo(**f)
assert str(foo) == 'Foo-5-10'

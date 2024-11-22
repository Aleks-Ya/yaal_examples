# Convert JSON to an object
import json


class Foo(object):
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __repr__(self):
        return f'Foo-{self.x}-{self.y}'


def test_json_to_object():
    content: str = '{"x": 5, "y": 10}'
    f: any = json.loads(content)
    foo: Foo = Foo(**f)
    assert str(foo) == 'Foo-5-10'

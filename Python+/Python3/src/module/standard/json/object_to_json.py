# Extract value from JSON by path


class Foo(object):
    def __init__(self):
        self.x = 1
        self.y = 2


def test_object_to_json():
    foo: Foo = Foo()
    json_obj: dict[str, any] = foo.__dict__
    s: dict[str, object] = json_obj.dumps()
    print(s)

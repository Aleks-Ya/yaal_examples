# Extract value from JSON by path
from typing import Any


class Foo(object):
    def __init__(self):
        self.x = 1
        self.y = 2


def test_object_to_json():
    foo: Foo = Foo()
    json_obj: dict[str, Any] = foo.__dict__
    print(json_obj)

# Extract value from JSON by path
import json
from typing import Dict


class Foo(object):
    def __init__(self):
        self.x = 1
        self.y = 2


foo = Foo()
json_obj: Dict[str, object] = foo.__dict__
s: Dict[str, object] = json_obj.dumps()
print(s)

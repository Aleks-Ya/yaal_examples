# Convert JSON to an object
import json
from types import SimpleNamespace
from typing import Any


def test_json_to_object():
    data: str = '{"name": "John Smith", "hometown": {"name": "New York", "id": 123}}'

    obj: Any = json.loads(data, object_hook=lambda d: SimpleNamespace(**d))

    assert obj.name == "John Smith"
    assert obj.hometown.name == "New York"
    assert obj.hometown.id == 123

    # Has field?
    assert hasattr(obj, 'name')
    assert not hasattr(obj, 'absent')

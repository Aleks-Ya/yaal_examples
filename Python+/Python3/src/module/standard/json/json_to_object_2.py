# Convert JSON to an object
import json
from types import SimpleNamespace

data = '{"name": "John Smith", "hometown": {"name": "New York", "id": 123}}'

obj = json.loads(data, object_hook=lambda d: SimpleNamespace(**d))

assert obj.name == "John Smith"
assert obj.hometown.name == "New York"
assert obj.hometown.id == 123

# Has field?
assert hasattr(obj, 'name')
assert not hasattr(obj, 'absent')

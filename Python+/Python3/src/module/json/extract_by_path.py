# Extract value from JSON by path
import json
from typing import Dict

data = '{"a": {"b": 7}}'
json_obj: Dict[str, object] = json.loads(data)
value: int = json_obj.get('a').get('b')
assert value == 7

# Get all fields
json_obj

# Extract value from JSON by path
import json

data = '{"a": {"b": 7}}'
json_obj = json.loads(data)
value = json_obj.get('a').get('b')
assert value == 7

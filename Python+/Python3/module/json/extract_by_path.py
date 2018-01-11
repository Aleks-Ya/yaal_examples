# Extract value from JSON by path
import json

data = '{"a": {"b": 7}}'
jsonObj = json.loads(data)
value = jsonObj.get('a').get('b')
assert value == 7

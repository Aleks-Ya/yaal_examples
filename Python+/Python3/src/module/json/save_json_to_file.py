# Save JSON to a file
import json
import tempfile

data = '{"a": {"b": 7}}'
json_obj = json.loads(data)

f = tempfile.mkstemp()[1]

with open(f, 'w') as fp:
    json.dump(json_obj, fp, indent=2)

with open(f) as fp:
    act_content = fp.read()

print(act_content)
assert act_content == '''{
  "a": {
    "b": 7
  }
}'''

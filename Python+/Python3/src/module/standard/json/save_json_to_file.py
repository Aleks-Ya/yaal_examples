# Save JSON to a file
import json
import tempfile
from typing import Dict

data: str = '{"a": {"b": 7}}'
json_obj: Dict[str, object] = json.loads(data)

f: str = tempfile.mkstemp()[1]

with open(f, 'w') as fp:
    json.dump(json_obj, fp, indent=2)

with open(f) as fp:
    act_content: str = fp.read()

assert act_content == '''{
  "a": {
    "b": 7
  }
}'''

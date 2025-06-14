import json
from typing import Any

import jsonschema

schema_str: str = """
{
  "type": "object",
  "properties": {
    "name": { "type": "string" }
  },
  "required": ["name"]
}
"""
schema_dict: dict[str, Any] = json.loads(schema_str)
doc_str: str = """
{"name": "Alice"}
"""
doc_dict: dict[str, Any] = json.loads(doc_str)
jsonschema.validate(instance=doc_dict, schema=schema_dict)

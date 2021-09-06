# Construct JSON in memory (without parsing strings and reading files)
import json
from typing import Dict, List, Tuple

# Dict to JSON
dict1: Dict[str, object] = {'field1': 1, 'field2': 'abc'}
json1: str = json.dumps(dict1)
assert json1 == '{"field1": 1, "field2": "abc"}'

# List to JSON
list2: List[Dict[str, object]] = [{'a': 54}, {'b': 'abc', 'c': 87}]
json2: str = json.dumps(list2)
assert json2 == '[{"a": 54}, {"b": "abc", "c": 87}]'

# Tuple to JSON
tuple3: Tuple[Dict[str, object], Dict[str, object]] = ({'a': 54}, {'b': 'abc', 'c': 87})
json3: str = json.dumps(tuple3)
assert json3 == '[{"a": 54}, {"b": "abc", "c": 87}]'

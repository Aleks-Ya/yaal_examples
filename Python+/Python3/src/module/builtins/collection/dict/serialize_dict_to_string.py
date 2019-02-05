# Serialize dict to a string and deserialize it back

dictionary = {'one': 1, 'two': 2, 'three': 3}

import json

serialized = json.dumps(dictionary)
assert serialized == '{"one": 1, "two": 2, "three": 3}'

deserialized = json.loads(serialized)
assert deserialized == dictionary

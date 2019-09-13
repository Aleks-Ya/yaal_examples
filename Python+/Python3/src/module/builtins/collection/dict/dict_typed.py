# Strongly typed Dict

# Strongly typed Dict
from typing import Dict

error: RuntimeError = RuntimeError()
typed_dict: Dict[str, RuntimeError] = {'error': error}
assert typed_dict['error'] == error

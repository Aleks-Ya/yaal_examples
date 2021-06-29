# Python "ClassPath"
import sys
from importlib._bootstrap_external import FileFinder
from typing import List, Dict

# Print Search path for modules
print('=== sys.path ===')
sys_path: List[str] = sys.path
for path in sys_path:
    print(path)
print()

# Print Search path for modules
print('=== sys.path_importer_cache ===')
path_importer_cache: Dict[str, FileFinder] = sys.path_importer_cache
for k, v in path_importer_cache.items():
    print(f'{k} - {v}')

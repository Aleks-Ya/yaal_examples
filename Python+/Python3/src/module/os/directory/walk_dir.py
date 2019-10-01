# Walk throughout a directory
import os
from typing import Generator, Any, List, Iterator, Tuple

root_dir = '..'

# Iterate subdirectories
for subdir, dirs, files in os.walk(root_dir):
    for file in files:
        print(os.path.join(subdir, file))
print()

# Typed
iterator: Iterator[Tuple[str, List[str], List[str]]] = os.walk(root_dir)
for tup in iterator:
    subdir: str = tup[0]
    dirs: List[str] = tup[1]
    files: List[str] = tup[2]
    print(f'{subdir}, {dirs}, {files}')
print()

# Filter subdirectories: directory contains file
res = [tup for tup in os.walk(root_dir) if 'walk_dir.py' in tup[2]]
print(res)
print()

# Filter subdirectories: directory contains subdir
res = [tup for tup in os.walk(root_dir) if 'file' in tup[1]]
print(res)
print()

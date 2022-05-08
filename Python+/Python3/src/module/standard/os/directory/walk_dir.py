# Walk throughout a directory
import os
from typing import List, Iterator, Tuple

root_dir = '..'

print('List all files in a tree')
for subdir, dirs, files in os.walk(root_dir):
    for file in files:
        print(os.path.join(subdir, file))
print()

print('List all subdirectories')
for subdir, dirs, files in os.walk(root_dir):
    for directory in dirs:
        print(os.path.join(subdir, directory))
print()

print('Typed')
iterator: Iterator[Tuple[str, List[str], List[str]]] = os.walk(root_dir)
for tup in iterator:
    subdir: str = tup[0]
    dirs: List[str] = tup[1]
    files: List[str] = tup[2]
    print(f'{subdir}, {dirs}, {files}')
print()

print('Filter subdirectories: directory contains a file')
contain_file: List[Tuple[str, List[str], List[str]]] = [tup for tup in os.walk(root_dir) if 'walk_dir.py' in tup[2]]
print(contain_file)
print()

print('Filter subdirectories: directory contains a subdir')
contain_subdir: List[Tuple[str, List[str], List[str]]] = [tup for tup in os.walk(root_dir) if 'file' in tup[1]]
print(contain_subdir)
print()

print('Filter subdirectories: directory by name')
dir_for_search = 'file_system'
dirs_by_name: List[Tuple[str, List[str], List[str]]] = \
    [tup for tup in os.walk(root_dir) if tup[0] == dir_for_search or tup[0].endswith(os.sep + dir_for_search)]
print(dirs_by_name)
print()

print('Filter subdirectories: directory contains files')
contain_file: List[Tuple[str, List[str], List[str]]] = [tup for tup in os.walk(root_dir) if len(tup[2]) > 0]
print(contain_file)
print()

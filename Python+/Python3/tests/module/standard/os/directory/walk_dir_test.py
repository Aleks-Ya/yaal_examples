# Walk throughout a directory
import os
from typing import List, Iterator, Tuple

root_dir = '..'


def test_list_all_files_in_a_tree():
    for subdir, dirs, files in os.walk(root_dir):
        for file in files:
            print(os.path.join(subdir, file))


def test_list_all_subdirectories():
    for subdir, dirs, files in os.walk(root_dir):
        for directory in dirs:
            print(os.path.join(subdir, directory))


def test_list_all_files_in_a_tree_typed():
    iterator: Iterator[Tuple[str, List[str], List[str]]] = os.walk(root_dir)
    for tup in iterator:
        subdir: str = tup[0]
        dirs: List[str] = tup[1]
        files: List[str] = tup[2]
        print(f'{subdir}, {dirs}, {files}')


def test_filter_subdirectories_by_file():
    contain_file: List[Tuple[str, List[str], List[str]]] = [tup for tup in os.walk(root_dir) if 'walk_dir_test.py' in tup[2]]
    print(contain_file)


def test_filter_subdirectories_by_subdir():
    contain_subdir: List[Tuple[str, List[str], List[str]]] = [tup for tup in os.walk(root_dir) if 'file' in tup[1]]
    print(contain_subdir)


def test_filter_subdirectories_by_name():
    dir_for_search: str = 'file_system'
    dirs_by_name: List[Tuple[str, List[str], List[str]]] = \
        [tup for tup in os.walk(root_dir) if tup[0] == dir_for_search or tup[0].endswith(os.sep + dir_for_search)]
    print(dirs_by_name)


def test_filter_subdirectories_by_file_2():
    contain_file: List[Tuple[str, List[str], List[str]]] = [tup for tup in os.walk(root_dir) if len(tup[2]) > 0]
    print(contain_file)

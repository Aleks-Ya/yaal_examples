import os
from pathlib import Path


def test_iterate_parents_absolute_path():
    p: Path = Path(os.sep, 'tmp', 'dir1', 'dir2', 'file.txt')
    parent_list: list[Path] = []
    for parent in p.parents:
        parent_list.append(parent)
    assert parent_list == [
        Path('/tmp/dir1/dir2'),
        Path('/tmp/dir1'),
        Path('/tmp'),
        Path('/')
    ]


def test_iterate_parents_relative_path():
    p: Path = Path('dir1', 'dir2', 'file.txt')
    parent_list: list[Path] = []
    for parent in p.parents:
        parent_list.append(parent)
    assert parent_list == [
        Path('dir1/dir2'),
        Path('dir1'),
        Path('.')
    ]

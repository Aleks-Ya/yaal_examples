from pathlib import Path


def test_path_join():
    parent: Path = Path('/parent/dir/')
    child: Path = parent.joinpath('child.txt')
    assert str(child) == '/parent/dir/child.txt'

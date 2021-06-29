from pathlib import Path

parent: Path = Path('/parent/dir/')
child: Path = parent.joinpath('child.txt')
assert str(child) == '/parent/dir/child.txt'

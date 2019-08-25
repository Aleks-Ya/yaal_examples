from pathlib import Path

# Get parent directory name
p: Path = Path('/tmp/work/data.txt')
parent_dir = p.parent
parent_dir_name = p.parent.name
assert str(parent_dir) == '/tmp/work'
assert parent_dir_name == 'work'

# Init Path
p2 = Path('/', 'tmp', 'work', 'data.txt')
assert str(p2) == '/tmp/work/data.txt'

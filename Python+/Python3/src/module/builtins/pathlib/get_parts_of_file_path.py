from pathlib import Path

# Get parent directory name
p: Path = Path('/tmp/work/data.txt')
parent_dir = p.parent
parent_dir_name = p.parent.name
file_name = p.name
base_name = p.stem
extension = p.suffix
assert str(parent_dir) == '/tmp/work'
assert parent_dir_name == 'work'
assert file_name == 'data.txt'
assert base_name == 'data'
assert extension == '.txt'

# Init Path
p2 = Path('/', 'tmp', 'work', 'data.txt')
assert str(p2) == '/tmp/work/data.txt'

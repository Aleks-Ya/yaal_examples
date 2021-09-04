from pathlib import Path

p = Path('/', 'tmp', 'work', 'data.txt')
assert str(p) == '/tmp/work/data.txt'

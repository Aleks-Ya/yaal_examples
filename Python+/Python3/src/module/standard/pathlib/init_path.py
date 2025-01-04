from pathlib import Path


def test_path_creation():
    p: Path = Path('/', 'tmp', 'work', 'data.txt')
    assert str(p) == '/tmp/work/data.txt'

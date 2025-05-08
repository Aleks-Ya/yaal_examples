from pathlib import Path


def test_path_creation():
    p: Path = Path('/', 'tmp', 'work', 'data.txt')
    assert str(p) == '/tmp/work/data.txt'


def test_file_in_current_dir():
    p: Path = Path('get_user_home_dir.py')
    assert p.exists()

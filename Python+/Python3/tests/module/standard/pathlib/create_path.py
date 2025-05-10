import os
from pathlib import Path


def test_path_creation():
    p: Path = Path(os.sep, 'tmp', 'work', 'data.txt')
    assert str(p) == '/tmp/work/data.txt'


def test_file_in_current_dir():
    current_dir: str = os.path.dirname(__file__)
    p: Path = Path(current_dir, 'create_path.py')
    assert p.exists()


def test_root_dir():
    p: Path = Path(os.sep)
    assert p == Path('/')


def test_user_home_dir():
    user_home_dir: Path = Path.home()
    assert user_home_dir is not None


def test_path_join():
    parent: Path = Path('/parent/dir/')
    child: Path = parent.joinpath('child.txt')
    assert str(child) == '/parent/dir/child.txt'


def test_relative_to_absolute():
    relative: Path = Path('tmp', 'data.txt')
    absolute: Path = relative.resolve()
    assert str(relative) == 'tmp/data.txt'
    assert absolute == Path.cwd() / 'tmp' / 'data.txt'


def test_relative():
    root_dir: Path = Path(os.sep, 'tmp', 'work', 'info', 'data.txt')
    file: Path = root_dir / 'details' / 'facts.txt'
    relative_path: Path = file.relative_to(root_dir)
    assert str(relative_path) == 'details/facts.txt'

from pathlib import Path


def test_pathlib_functions():
    p: Path = Path('/tmp/work/data.txt')
    parent_dir: Path = p.parent
    parent_dir_name: str = p.parent.name
    file_name: str = p.name
    base_name: str = p.stem
    extension: str = p.suffix

    assert str(parent_dir) == '/tmp/work'
    assert parent_dir_name == 'work'
    assert file_name == 'data.txt'
    assert base_name == 'data'
    assert extension == '.txt'

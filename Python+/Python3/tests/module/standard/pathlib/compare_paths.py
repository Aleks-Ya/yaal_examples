from pathlib import Path


def test_path_equals():
    p1: Path = Path('/tmp/work/data.txt')
    p2: Path = Path('/tmp', 'work', 'data.txt')
    assert p1 == p2


def test_path_in():
    paths: list[Path] = [Path('/tmp/work/data1.txt'), Path('/tmp/work/data2.txt')]
    path: Path = Path('/tmp/work/data2.txt')
    assert path in paths

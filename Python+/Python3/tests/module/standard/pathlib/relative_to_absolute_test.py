from pathlib import Path


def test_relative_to_absolute():
    relative: Path = Path('tmp', 'data.txt')
    absolute: Path = relative.resolve()
    assert str(relative) == 'tmp/data.txt'
    assert absolute == Path.cwd() / 'tmp' / 'data.txt'

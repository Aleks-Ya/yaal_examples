import tempfile
from pathlib import Path


def test_calculate_folder_size():
    folder: Path = Path(tempfile.mkdtemp())
    folder.joinpath('file1.txt').write_text('abc')
    folder.joinpath('file2.txt').write_text('xyz')
    size: int = sum(f.stat().st_size for f in folder.glob('**/*') if f.is_file())
    assert size == 6

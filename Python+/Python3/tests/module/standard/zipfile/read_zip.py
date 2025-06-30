import tempfile
from zipfile import ZipFile
from pathlib import Path

from current_path import get_file_in_current_dir


def test_read_single_file_from_zip():
    zip_file: Path = get_file_in_current_dir('data.zip')
    with ZipFile(zip_file, 'r') as zr:
        with zr.open('data.txt') as file:
            content: str = file.read().decode()
    assert "abc" == content


def test_extract_single_file_from_zip():
    zip_file: Path = get_file_in_current_dir('data.zip')
    with ZipFile(zip_file, 'r') as zr:
        dest_dir: Path = Path(tempfile.mkdtemp())
        file_in_zip: str = 'info.txt'
        zr.extract(file_in_zip, dest_dir)
    assert "xyz" == dest_dir.joinpath(file_in_zip).read_text()


def test_extract_single_file_from_zip_with_custom_name():
    zip_file: Path = get_file_in_current_dir('data.zip')
    with ZipFile(zip_file, 'r') as zr:
        dest_dir: Path = Path(tempfile.mkdtemp())
        file_in_zip: str = 'info.txt'
        dest_file: Path = dest_dir / 'details.txt'
        with zr.open(file_in_zip) as source, open(dest_file, 'wb') as target:
            target.write(source.read())
    assert "xyz" == dest_dir.joinpath(dest_file).read_text()

from zipfile import ZipFile
from pathlib import Path

from temp_helper import TempPath


def test_create_empty_zip():
    zip_file: Path = TempPath.temp_path_absent(".zip")
    assert not zip_file.exists()
    with ZipFile(zip_file, "w"):
        pass
    assert zip_file.exists()
    with ZipFile(zip_file, 'r') as zr:
        assert zr.testzip() is None


def test_create_zip():
    zip_file: Path = TempPath.temp_path_absent(".zip")
    assert not zip_file.exists()
    with ZipFile(zip_file, "w") as zw:
        zw.writestr("data.txt", "abc")
    assert zip_file.exists()
    with ZipFile(zip_file, 'r') as zr:
        assert zr.testzip() is None
        assert zr.read("data.txt") == b"abc"

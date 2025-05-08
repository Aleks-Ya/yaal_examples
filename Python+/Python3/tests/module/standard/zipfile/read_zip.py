import tempfile
import unittest
import zipfile
from pathlib import Path


class TestReadZip(unittest.TestCase):

    def test_read_single_file_from_zip(self):
        with zipfile.ZipFile('data.zip', 'r') as zr:
            with zr.open('data.txt') as file:
                content: str = file.read().decode()
        self.assertEqual("abc", content)

    def test_extract_single_file_from_zip(self):
        with zipfile.ZipFile('data.zip', 'r') as zr:
            dest_dir: Path = Path(tempfile.mkdtemp())
            file_in_zip: str = 'info.txt'
            zr.extract(file_in_zip, dest_dir)
        self.assertEqual("xyz", dest_dir.joinpath(file_in_zip).read_text())

    def test_extract_single_file_from_zip_with_custom_name(self):
        with zipfile.ZipFile('data.zip', 'r') as zr:
            dest_dir: Path = Path(tempfile.mkdtemp())
            file_in_zip: str = 'info.txt'
            dest_file: Path = dest_dir / 'details.txt'
            with zr.open(file_in_zip) as source, open(dest_file, 'wb') as target:
                target.write(source.read())
        self.assertEqual("xyz", dest_dir.joinpath(dest_file).read_text())


if __name__ == '__main__':
    unittest.main()

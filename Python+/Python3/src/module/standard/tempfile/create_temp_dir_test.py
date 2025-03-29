import os
import tempfile
from tempfile import TemporaryDirectory


def test_mkdtemp():
    full_name: str = tempfile.mkdtemp()
    assert os.path.isdir(full_name)
    assert os.path.exists(full_name)
    os.removedirs(full_name)


def test_temporary_directory():
    with TemporaryDirectory() as td:
        assert os.path.isdir(td)
        assert os.path.exists(td)
        os.removedirs(td)

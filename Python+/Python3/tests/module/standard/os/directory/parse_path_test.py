# Get directory name from full file name
import os


def test_dirname_of_file():
    file_name: str = '/tmp/work/data.txt'
    dir_name: str = os.path.dirname(file_name)
    assert dir_name == '/tmp/work'


def test_basename():
    file_name: str = '/tmp/work/data.txt'
    dir_name: str = os.path.dirname(file_name)
    assert dir_name == '/tmp/work'
    base_name: str = os.path.basename(dir_name)
    assert base_name == 'work'


def test_dirname_of_dir():
    file_name: str = '/tmp/work/data.txt'
    dir_name: str = os.path.dirname(file_name)
    assert dir_name == '/tmp/work'
    top_dir_name: str = os.path.dirname(dir_name)
    assert top_dir_name == '/tmp'

import os


def test_is_dir_exists():
    assert os.path.isdir('.')
    assert not os.path.isdir('../not_exists')


def test_is_file_or_dir_exists():
    assert os.path.exists('is_dir_exists_test.py')
    assert not os.path.exists('../not_exists')

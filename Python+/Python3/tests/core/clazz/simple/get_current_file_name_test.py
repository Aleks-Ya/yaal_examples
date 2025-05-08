import os

full_path = __file__
file_with_extension = os.path.basename(full_path)
file_without_extension = os.path.splitext(file_with_extension)[0]
dir_name = os.path.dirname(full_path)


def test_full_path():
    assert str(full_path).endswith('Python+/Python3/src/core/clazz/simple/get_current_file_name_test.py')


def test_file_with_extension():
    assert file_with_extension == 'get_current_file_name_test.py'


def test_file_without_extension():
    assert file_without_extension == 'get_current_file_name_test'


def test_dir_name():
    assert str(dir_name).endswith('Python+/Python3/src/core/clazz/simple')

import os
import tempfile


def test_create_temp_file():
    file_descriptor, full_name = tempfile.mkstemp()
    assert file_descriptor is not None
    assert os.path.isfile(full_name)


def test_create_temp_file_with_given_extension():
    file_descriptor, full_name = tempfile.mkstemp(suffix='.txt')
    assert file_descriptor is not None
    assert os.path.isfile(full_name)
    assert full_name.endswith('.txt')

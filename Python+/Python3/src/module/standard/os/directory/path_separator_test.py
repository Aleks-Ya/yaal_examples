import os

def test_path_separator():
    folder: str = os.getcwd()
    file: str = 'join_paths_test.py'
    full_path: str = os.path.join(folder, file)

    full_path_by_sep: str = folder + os.sep + file

    assert (full_path == full_path_by_sep)

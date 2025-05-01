import os


def test_join_paths():
    current_dir: str = os.getcwd()
    file_name: str = 'join_paths_test.py'
    full_file_path: str = os.path.join(current_dir, file_name)
    print(f'Full path: {full_file_path}')
    assert (full_file_path.endswith('/os/directory/join_paths_test.py'))

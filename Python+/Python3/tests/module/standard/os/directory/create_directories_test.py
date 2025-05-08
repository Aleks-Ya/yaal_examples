import os
import tempfile


def test_create_directories():
    tmp_dir: str = tempfile.mkdtemp()
    not_exist_path: str = tmp_dir + "/d1/d2"
    print("Full name: " + not_exist_path)
    assert not os.path.isdir(not_exist_path)


def test_create_directories_with_exist_ok():
    tmp_dir: str = tempfile.mkdtemp()
    not_exist_path: str = tmp_dir + "/d1/d2"
    os.makedirs(not_exist_path)
    os.makedirs(not_exist_path, exist_ok=True)
    assert os.path.isdir(not_exist_path)
    os.removedirs(not_exist_path)

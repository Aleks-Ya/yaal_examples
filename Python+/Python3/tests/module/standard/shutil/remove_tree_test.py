import os
import shutil
import tempfile


def test_remove_tree():
    tmp_dir: str = tempfile.mkdtemp()

    tmp_file: str = os.path.join(tmp_dir, 'file1.txt')
    with open(tmp_file, 'w') as f:
        f.write('abc')
    assert os.path.isdir(tmp_dir)
    assert os.path.isfile(tmp_file)

    shutil.rmtree(tmp_dir)
    assert not os.path.isfile(tmp_file)
    assert not os.path.isdir(tmp_dir)

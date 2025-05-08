import os
import shutil
import tempfile


def test_copy_file_to_file():
    with tempfile.NamedTemporaryFile(suffix=".tmp", delete=False) as source_file:
        exp_content: str = 'abc'
        source_file.write(exp_content.encode())
        source_file_path = source_file.name

    with tempfile.NamedTemporaryFile(suffix=".tmp", delete=False) as dest_file:
        dest_file_path = dest_file.name
    os.remove(dest_file_path)

    shutil.copyfile(source_file_path, dest_file_path)

    with open(dest_file_path) as f:
        act_content = f.read()
    assert exp_content == act_content


def test_copy_file_to_dir():
    with tempfile.NamedTemporaryFile(suffix=".tmp", delete=False) as source_file:
        exp_content: str = 'abc'
        source_file.write(exp_content.encode())
        source_file_path = source_file.name

    with tempfile.TemporaryDirectory() as dest_dir:
        shutil.copy(source_file_path, dest_dir)

        dest_file: str = os.path.join(dest_dir, os.path.basename(source_file_path))
        with open(dest_file) as f:
            act_content = f.read()
        assert exp_content == act_content

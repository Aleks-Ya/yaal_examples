import os
import shutil
import tempfile
from pathlib import Path
from typing import Callable, Any

import pytest


@pytest.fixture
def src_and_dest_dirs():
    src_dir: Path = Path(tempfile.mkdtemp())
    dest_dir: Path = Path(tempfile.mkdtemp())
    return src_dir, dest_dir

def test_copy_tree(src_and_dest_dirs):
    src_dir, dest_dir = src_and_dest_dirs
    filename: str = 'file1.txt'
    src_file: Path = src_dir.joinpath(filename)
    exp_content: str = 'abc'
    src_file.write_text(exp_content)
    os.removedirs(dest_dir)
    dest_file: Path = dest_dir.joinpath(filename)
    shutil.copytree(src_dir, dest_dir)
    act_content = dest_file.read_text()
    assert exp_content == act_content

def test_copy_tree_ignore(src_and_dest_dirs):
    src_dir, dest_dir = src_and_dest_dirs
    filename_included: str = 'file.txt'
    filename_ignored: str = 'file.tmp'
    src_file_included: Path = src_dir.joinpath(filename_included)
    src_file_ignored: Path = src_dir.joinpath(filename_ignored)
    src_file_included.touch()
    src_file_ignored.touch()
    os.removedirs(dest_dir)
    dest_file_included: Path = dest_dir.joinpath(filename_included)
    dest_file_ignored: Path = dest_dir.joinpath(filename_ignored)

    ignore_patterns: Callable[[Any, list[str]], set[str]] = shutil.ignore_patterns("*.tmp")
    shutil.copytree(src_dir, dest_dir, ignore=ignore_patterns)

    assert dest_file_included.exists()
    assert not dest_file_ignored.exists()

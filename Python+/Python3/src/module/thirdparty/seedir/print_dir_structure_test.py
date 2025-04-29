from pathlib import Path

from seedir import seedir


def test_print_dir_structure():
    folder: Path = Path.cwd().parent
    seedir(folder)

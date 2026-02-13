from pathlib import Path

from seedir import seedir


def test_print_dir_structure():
    folder: Path = Path.cwd().parent
    seedir(folder)


def test_dir_structure_to_str():
    folder: Path = Path.cwd().parent
    tree: str = seedir(folder, printout=False)
    print(tree)


def __pem_mask(p: Path) -> bool:
    if p.suffix != '.pem':
        return False
    return True


def test_mask():
    folder: Path = Path('/tmp')
    tree: str = seedir(folder, mask=__pem_mask, printout=False)
    print(tree)

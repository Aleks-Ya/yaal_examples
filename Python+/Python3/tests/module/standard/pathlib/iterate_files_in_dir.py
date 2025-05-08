from pathlib import Path


def test_iterdir():
    p: Path = Path('/tmp/')
    for child in p.iterdir():
        if child.is_file():
            print(f'File: {child}')
        else:
            print(f'Dir: {child}')


def test_glob():
    p: Path = Path('/tmp/')
    for child in p.glob("*.tmp"):
        if child.is_file():
            print(f'File: {child}')
        else:
            print(f'Dir: {child}')

import sys

from importlib._bootstrap_external import FileFinder


def test_path():
    sys_path: list[str] = sys.path
    for path in sys_path:
        print(path)
    print()


def test_path_importer_cache():
    path_importer_cache: dict[str, FileFinder] = sys.path_importer_cache
    for k, v in path_importer_cache.items():
        print(f'{k} - {v}')

from pathlib import Path

from seedir import seedir


class SeeDirFormatter:
    def __init__(self, base_dir: Path, paths: list[Path]):
        self.__base_dir = base_dir
        self.__paths = paths

    def format(self) -> str:
        tree: str = seedir(self.__base_dir, mask=self.__mask, printout=False)
        return tree

    def __mask(self, path: Path) -> bool:
        for p in self.__paths:
            if path == p or path in p.parents:
                return True
        return False

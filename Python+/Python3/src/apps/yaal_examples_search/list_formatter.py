import re
from pathlib import Path
from re import Pattern


class ListFormatter:
    __red_start: str = '\033[31m'
    __red_end: str = '\033[0m'

    def __init__(self, base_dir: Path, paths: list[Path]):
        self.__paths = paths
        self.__base_dir = base_dir

    def format(self, word: str) -> str:
        return '\n'.join([self.__paint_word(str(p.relative_to(self.__base_dir)), word) for p in self.__paths])

    def __paint_word(self, text: str, word: str) -> str:
        pattern: Pattern[str] = re.compile(re.escape(word), re.IGNORECASE)
        return pattern.sub(lambda m: self.__red_start + m.group(0) + self.__red_end, text)

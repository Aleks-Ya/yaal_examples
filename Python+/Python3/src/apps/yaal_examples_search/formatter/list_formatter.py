import re
from pathlib import Path
from re import Pattern

from apps.yaal_examples_search.data_types import Keyword
from apps.yaal_examples_search.formatter.color import Color


class ListFormatter:
    def __init__(self, base_dir: Path, paths: list[Path]):
        self.__paths = paths
        self.__base_dir = base_dir

    def format(self, keyword: Keyword) -> str:
        return '\n'.join([self.__paint_keyword(str(p.relative_to(self.__base_dir)), keyword) for p in self.__paths])

    def __paint_keyword(self, text: str, keyword: Keyword) -> str:
        pattern: Pattern[str] = re.compile(re.escape(keyword), re.IGNORECASE)
        return pattern.sub(lambda m: Color.RED + m.group(0) + Color.RESET, text)

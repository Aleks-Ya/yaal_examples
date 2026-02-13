from pathlib import Path
from typing import Optional


class Searcher:
    def __init__(self, filtered_files: list[Path]):
        self.__filtered_files = filtered_files

    def search_word(self, word: str) -> set[Path]:
        lower_word: str = word.casefold()
        result_set: set[Path] = set()
        for f in self.__filtered_files:
            found_path: Optional[Path] = self.__get_path_to_word(lower_word, f)
            if found_path is not None:
                result_set.add(found_path)
        return result_set

    @staticmethod
    def __get_path_to_word(lower_word: str, path: Path) -> Optional[Path]:
        out_path: Optional[Path] = Path('/')
        for part in path.parts:
            if lower_word in part.casefold():
                return out_path / part
            else:
                out_path /= part
        return None

from pathlib import Path
from typing import Optional

from apps.yaal_examples_search.data_types import Keywords


class Searcher:
    def __init__(self, files: list[Path]):
        self.__files = files

    def search(self, keywords: Keywords) -> set[Path]:
        result_set: set[Path] = set()
        for f in self.__files:
            if self.__matches_all_keywords(f, keywords):
                found_path: Optional[Path] = self.__get_deepest_match(f, keywords)
                if found_path is not None:
                    result_set.add(found_path)
        return result_set

    @staticmethod
    def __matches_all_keywords(path: Path, keywords: Keywords) -> bool:
        path_str: str = str(path).casefold()
        return all(keyword.casefold() in path_str for keyword in keywords)

    @staticmethod
    def __get_deepest_match(path: Path, keywords: Keywords) -> Optional[Path]:
        out_path: Path = Path("/")
        remaining: set[str] = {keyword.casefold() for keyword in keywords}

        for part in path.parts:
            part_lower: str = part.casefold()
            matched_now = {k for k in remaining if k in part_lower}
            remaining -= matched_now

            out_path /= part
            if not remaining:
                return out_path

        return None

from pathlib import Path
from typing import Optional

from apps.yaal_examples_search.data_types import Keywords


class Searcher:
    def __init__(self, files: list[Path], case_sensitive: bool = False):
        self.__files = files
        self.__case_sensitive = case_sensitive

    def search(self, keywords: Keywords) -> set[Path]:
        result_set: set[Path] = set()
        for f in self.__files:
            if self.__matches_all_keywords(f, keywords):
                found_path: Optional[Path] = self.__get_deepest_match(f, keywords)
                if found_path is not None:
                    result_set.add(found_path)
        return result_set

    def __normalize(self, value: str) -> str:
        if self.__case_sensitive:
            return value
        return value.casefold()

    def __matches_all_keywords(self, path: Path, keywords: Keywords) -> bool:
        path_str: str = self.__normalize(str(path))
        return all(self.__normalize(keyword) in path_str for keyword in keywords)

    def __get_deepest_match(self, path: Path, keywords: Keywords) -> Optional[Path]:
        out_path: Path = Path("/")
        remaining: set[str] = {self.__normalize(keyword) for keyword in keywords}

        for part in path.parts:
            normalized_part: str = self.__normalize(part)
            matched_now = {k for k in remaining if k in normalized_part}
            remaining -= matched_now

            out_path /= part
            if not remaining:
                return out_path

        return None
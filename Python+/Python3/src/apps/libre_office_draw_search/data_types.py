from dataclasses import dataclass
from pathlib import Path
from typing import NewType

FodgPath = NewType("FodgPath", Path)
FileName = NewType("FileName", str)
PageName = NewType("PageName", str)
Text = NewType("Text", str)


@dataclass
class SearchResult:
    rank: int
    draw_file: FodgPath
    file_names: list[FileName]
    page_names: list[PageName]
    texts: list[Text]

    def found_filename(self) -> bool:
        return len(self.file_names) > 0

    def found_pages(self) -> bool:
        return len(self.page_names) > 0

    def found_texts(self) -> bool:
        return len(self.texts) > 0

    def found(self) -> bool:
        return self.found_filename() or self.found_pages() or self.found_texts()


@dataclass
class SearchResults:
    results: list[SearchResult]
    pages_count: int
    texts_count: int
    matches_count: int

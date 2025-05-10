from dataclasses import dataclass
from pathlib import Path
from typing import NewType

FodgPath = NewType("FodgPath", Path)
FolderName = NewType("FolderName", str)
FileName = NewType("FileName", str)
PageName = NewType("PageName", str)
Text = NewType("Text", str)


@dataclass
class SearchResult:
    rank: int
    draw_file: FodgPath
    folder_names: list[FolderName]
    file_names: list[FileName]
    page_names: list[PageName]
    texts: list[Text]

    def are_folder_names_found(self) -> bool:
        return len(self.folder_names) > 0

    def are_filenames_found(self) -> bool:
        return len(self.file_names) > 0

    def are_pages_found(self) -> bool:
        return len(self.page_names) > 0

    def are_texts_found(self) -> bool:
        return len(self.texts) > 0

    def is_found(self) -> bool:
        return self.are_folder_names_found() or self.are_filenames_found() or self.are_pages_found() or self.are_texts_found()


@dataclass
class SearchResults:
    results: list[SearchResult]
    pages_count: int
    texts_count: int
    matches_count: int

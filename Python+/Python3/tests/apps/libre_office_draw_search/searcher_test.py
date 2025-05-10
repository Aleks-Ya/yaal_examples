from pathlib import Path

from apps.libre_office_draw_search.data_types import FodgPath, Text, SearchResult, SearchResults, FolderName
from apps.libre_office_draw_search.searcher import Searcher


def test_search(root_dir_real: Path):
    keywords: list[str] = ["house"]
    buildings_file: Path = root_dir_real / "nested" / "buildings.fodg"
    files: list[FodgPath] = [FodgPath(buildings_file)]
    searcher: Searcher = Searcher(root_dir_real)
    act_search_results: SearchResults = searcher.search(files, keywords)
    exp_search_results = SearchResults([
        SearchResult(rank=0, draw_file=FodgPath(buildings_file), folder_names=[],
                     file_names=[], page_names=[], texts=[Text("House")])
    ], 2, 3, 1)
    assert act_search_results == exp_search_results


def test_search_in_folder_names(root_dir_real: Path):
    keywords: list[str] = ["Nest"]
    buildings_file: Path = root_dir_real / "nested" / "buildings.fodg"
    files: list[FodgPath] = [FodgPath(buildings_file)]
    searcher: Searcher = Searcher(root_dir_real)
    act_search_results: SearchResults = searcher.search(files, keywords)
    exp_search_results = SearchResults([
        SearchResult(rank=0, draw_file=FodgPath(buildings_file), folder_names=[FolderName('nested')],
                     file_names=[], page_names=[], texts=[])
    ], 2, 3, 1)
    assert act_search_results == exp_search_results

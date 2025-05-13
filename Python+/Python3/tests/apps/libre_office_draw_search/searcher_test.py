from apps.libre_office_draw_search.data_types import FodgPath, Text, SearchResult, SearchResults, FolderName
from apps.libre_office_draw_search.searcher import Searcher


def test_search(real_root_dir: FodgPath, real_buildings_file: FodgPath):
    keywords: list[str] = ["house"]
    files: list[FodgPath] = [FodgPath(real_buildings_file)]
    searcher: Searcher = Searcher(real_root_dir)
    act_search_results: SearchResults = searcher.search(files, keywords)
    exp_search_results = SearchResults([
        SearchResult(rank=0, draw_file=FodgPath(real_buildings_file), folder_names=[],
                     file_names=[], page_names=[], texts=[Text("House")])
    ], 2, 3, 1)
    assert act_search_results == exp_search_results


def test_search_in_folder_names(real_root_dir: FodgPath, real_buildings_file: FodgPath):
    keywords: list[str] = ["Nest"]
    files: list[FodgPath] = [FodgPath(real_buildings_file)]
    searcher: Searcher = Searcher(real_root_dir)
    act_search_results: SearchResults = searcher.search(files, keywords)
    exp_search_results = SearchResults([
        SearchResult(rank=0, draw_file=FodgPath(real_buildings_file), folder_names=[FolderName('nested')],
                     file_names=[], page_names=[], texts=[])
    ], 2, 3, 1)
    assert act_search_results == exp_search_results

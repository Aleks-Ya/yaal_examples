from apps.libre_office_draw_search.data_types import OdgPath, Text, SearchResult, SearchResults, FolderName
from apps.libre_office_draw_search.searcher import Searcher


def test_search(real_root_dir: OdgPath, real_buildings_file: OdgPath):
    keywords: list[str] = ["house"]
    files: list[OdgPath] = [OdgPath(real_buildings_file)]
    searcher: Searcher = Searcher(real_root_dir)
    act_search_results: SearchResults = searcher.search(files, keywords)
    exp_search_results = SearchResults([
        SearchResult(rank=0, draw_file=OdgPath(real_buildings_file), folder_names=[],
                     file_names=[], page_names=[], texts=[Text("House")])
    ], 2, 3, 1)
    assert act_search_results == exp_search_results


def test_search_in_folder_names(real_root_dir: OdgPath, real_buildings_file: OdgPath):
    keywords: list[str] = ["Nest"]
    files: list[OdgPath] = [OdgPath(real_buildings_file)]
    searcher: Searcher = Searcher(real_root_dir)
    act_search_results: SearchResults = searcher.search(files, keywords)
    exp_search_results = SearchResults([
        SearchResult(rank=0, draw_file=OdgPath(real_buildings_file), folder_names=[FolderName('nested')],
                     file_names=[], page_names=[], texts=[])
    ], 2, 3, 1)
    assert act_search_results == exp_search_results

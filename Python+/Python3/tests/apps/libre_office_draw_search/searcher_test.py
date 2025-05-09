from pathlib import Path

from apps.libre_office_draw_search.data_types import FodgPath, Text, SearchResult, SearchResults
from apps.libre_office_draw_search.searcher import Searcher


def test_search():
    keywords: list[str] = ["house"]
    buildings_file: Path = Path(__file__).parent / "files" / "buildings.fodg"
    files: list[FodgPath] = [FodgPath(buildings_file)]
    act_search_results: SearchResults = Searcher.search(files, keywords)
    exp_search_results = SearchResults([
        SearchResult(rank=0, draw_file=FodgPath(buildings_file), file_names=[], page_names=[], texts=[Text("House")])
    ], 2, 3, 1)
    assert act_search_results == exp_search_results

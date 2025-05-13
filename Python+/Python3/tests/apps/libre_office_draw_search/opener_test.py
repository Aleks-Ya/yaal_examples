from apps.libre_office_draw_search.data_types import SearchResults, SearchResult
from apps.libre_office_draw_search.opener import Opener


def test_open_empty_results(search_result_1: SearchResult, search_result_2: SearchResult):
    matches_count: int = 0
    search_results: SearchResults = SearchResults([search_result_1, search_result_2], 5, 10, matches_count)
    Opener.open_result(search_results)

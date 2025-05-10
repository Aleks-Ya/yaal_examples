from apps.libre_office_draw_search.data_types import SearchResults
from apps.libre_office_draw_search.opener import Opener


def test_open_empty_results():
    search_results: SearchResults = SearchResults([], 0, 0, 0)
    Opener.open_result(search_results)

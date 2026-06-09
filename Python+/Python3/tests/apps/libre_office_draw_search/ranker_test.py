from pathlib import Path

from apps.libre_office_draw_search.data_types import OdgPath, FileName, PageName, SearchResult, SearchResults, \
    FolderName
from apps.libre_office_draw_search.ranker import Ranker


def test_rank():
    search_results: SearchResults = SearchResults([
        SearchResult(0, OdgPath(Path('file1.odg')), [FolderName('dir1')], [FileName("file1.odg")],
                     [PageName("Page 1")], []),
        SearchResult(0, OdgPath(Path('file2.odg')), [], [FileName("file2.odg")],
                     [PageName("Page 1"), PageName("Page 2")], [])
    ], 3, 0, 2)
    ranked_search_results: SearchResults = Ranker.rank_results(search_results)
    assert ranked_search_results == SearchResults([
        SearchResult(1, OdgPath(Path('file1.odg')), [FolderName('dir1')], [FileName("file1.odg")],
                     [PageName("Page 1")], []),
        SearchResult(2, OdgPath(Path('file2.odg')), [], [FileName("file2.odg")],
                     [PageName("Page 1"), PageName("Page 2")], []),
    ], 3, 0, 2)


def test_rank_empty():
    search_results: SearchResults = SearchResults([], 0, 0, 0)
    ranked_search_results: SearchResults = Ranker.rank_results(search_results)
    assert ranked_search_results == SearchResults([], 0, 0, 0)
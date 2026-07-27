import subprocess

import pytest

from apps.libre_office_draw_search.data_types import SearchResults, SearchResult
from apps.libre_office_draw_search.opener import Opener


def test_open_empty_results(search_result_1: SearchResult, search_result_2: SearchResult):
    matches_count: int = 0
    search_results: SearchResults = SearchResults([search_result_1, search_result_2], 5, 10, matches_count)
    Opener.open_result(search_results)


def test_rank_above_match_count_is_rejected(
        search_result_1: SearchResult, unmatched_search_result: SearchResult,
        monkeypatch: pytest.MonkeyPatch, capsys: pytest.CaptureFixture[str]):
    # search_result_1 is the only match (rank 1); unmatched_search_result has rank 2 but is not found.
    search_results: SearchResults = SearchResults([search_result_1, unmatched_search_result], 5, 10, 1)
    inputs = iter(["2", ""])
    monkeypatch.setattr("builtins.input", lambda _: next(inputs))
    opened: list = []
    monkeypatch.setattr(subprocess, "run", lambda args, **kwargs: opened.append(args))

    Opener.open_result(search_results)

    assert opened == []
    assert "Invalid rank selected." in capsys.readouterr().out


def test_non_numeric_input_is_rejected(
        search_result_1: SearchResult, unmatched_search_result: SearchResult,
        monkeypatch: pytest.MonkeyPatch, capsys: pytest.CaptureFixture[str]):
    search_results: SearchResults = SearchResults([search_result_1, unmatched_search_result], 5, 10, 1)
    inputs = iter(["abcd", ""])
    monkeypatch.setattr("builtins.input", lambda _: next(inputs))
    opened: list = []
    monkeypatch.setattr(subprocess, "run", lambda args, **kwargs: opened.append(args))

    Opener.open_result(search_results)

    assert opened == []
    assert "Invalid rank selected." in capsys.readouterr().out


def test_matched_rank_is_opened(
        search_result_1: SearchResult, unmatched_search_result: SearchResult,
        monkeypatch: pytest.MonkeyPatch):
    search_results: SearchResults = SearchResults([search_result_1, unmatched_search_result], 5, 10, 1)
    inputs = iter(["1", ""])
    monkeypatch.setattr("builtins.input", lambda _: next(inputs))
    opened: list = []
    monkeypatch.setattr(subprocess, "run", lambda args, **kwargs: opened.append(args))

    Opener.open_result(search_results)

    assert len(opened) == 1
    assert search_result_1.draw_file in opened[0]

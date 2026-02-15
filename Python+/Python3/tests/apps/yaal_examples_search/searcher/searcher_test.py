from pathlib import Path

import pytest

from apps.yaal_examples_search.data_types import Keyword, Keywords
from apps.yaal_examples_search.searcher.searcher import Searcher


def test_search_returns_empty_set_when_no_files_match() -> None:
    searcher: Searcher = Searcher([
        Path("/repo/src/python/app/main.py"),
        Path("/repo/src/java/App.java"),
    ])
    result: set[Path] = searcher.search(Keywords([Keyword("kotlin")]))
    assert result == set()


def test_search_is_case_insensitive_and_returns_deepest_match_part() -> None:
    searcher: Searcher = Searcher([
        Path("/repo/Src/Java/com/acme/App.java"),
    ])
    # "java" matches the "Java" path segment; the result should be the path up to that segment.
    result: set[Path] = searcher.search(Keywords([Keyword("jAvA")]))
    assert result == {Path("/repo/Src/Java")}


def test_search_requires_all_keywords_to_be_present_somewhere_in_the_full_path() -> None:
    searcher: Searcher = Searcher([
        Path("/repo/src/java/com/acme/App.java"),
        Path("/repo/src/java/com/other/Tool.java"),
        Path("/repo/src/python/com/acme/app.py"),
    ])
    result: set[Path] = searcher.search(Keywords([Keyword("java"), Keyword("acme")]))
    # Both keywords present -> included; deepest match is the first segment that matches any keyword ("java").
    assert result == {Path("/repo/src/java/com/acme")}


def test_search_deepest_match_is_first_segment_matching_any_keyword_not_the_deepest_occurrence() -> None:
    searcher: Searcher = Searcher([
        Path("/repo/java/src/java/App.java"),  # keyword appears twice
    ])
    result: set[Path] = searcher.search(Keywords([Keyword("java")]))
    # First matching segment is "java" (right after /repo), so it returns /repo/java, not /repo/java/src/java.
    assert result == {Path("/repo/java")}


def test_search_deduplicates_results_when_multiple_files_map_to_same_found_path() -> None:
    searcher: Searcher = Searcher([
        Path("/repo/src/java/com/acme/App.java"),
        Path("/repo/src/java/com/acme/Other.java"),
        Path("/repo/src/java/com/acme/internal/More.java"),
    ])
    result: set[Path] = searcher.search(Keywords([Keyword("java")]))
    assert result == {Path("/repo/src/java")}


@pytest.mark.parametrize(
    ("path", "keywords", "expected"),
    [
        (Path("/a/b/c.txt"), Keywords([Keyword("b")]), {Path("/a/b")}),
        (Path("/a/b/c.txt"), Keywords([Keyword("c")]), {Path("/a/b/c.txt")}),
        (Path("/a/b/c.txt"), Keywords([Keyword("c.txt")]), {Path("/a/b/c.txt")}),
        (Path("/a/b/c.txt"), Keywords([Keyword("missing")]), set()),
        (Path("/a/Alpha/b.txt"), Keywords([Keyword("alpha")]), {Path("/a/Alpha")}),
    ],
)
def test_search_various_single_keyword_cases(path: Path, keywords: Keywords, expected: set[Path]) -> None:
    searcher: Searcher = Searcher([path])
    assert searcher.search(keywords) == expected

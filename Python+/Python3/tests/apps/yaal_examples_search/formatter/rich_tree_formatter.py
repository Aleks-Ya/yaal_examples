from __future__ import annotations

from pathlib import Path
from textwrap import dedent

import pytest

from apps.yaal_examples_search.data_types import Keyword
from apps.yaal_examples_search.formatter.rich_tree_formatter import RichTreeFormatter, FormatResults


def test_format_empty_paths_prints_base_only(tmp_path: Path) -> None:
    base_dir: Path = tmp_path / "repo"
    base_dir.mkdir()
    formatter: RichTreeFormatter = RichTreeFormatter(base_dir, [])
    format_results: FormatResults = formatter.format([Keyword("whatever")])
    # Root label uses str(base) (resolved) and directories are rendered with a trailing "/"
    out: str = format_results.content
    assert out == dedent(f"""\
                        {base_dir.resolve()}/ [0]
                        """)


def test_format_ignores_paths_outside_base(tmp_path: Path) -> None:
    base_dir: Path = tmp_path / "repo"
    base_dir.mkdir()
    inside: Path = __touch(base_dir / "src" / "main.py")
    outside: Path = __touch(tmp_path / "elsewhere" / "nope.py")
    formatter: RichTreeFormatter = RichTreeFormatter(base_dir, [inside, outside])
    format_results: FormatResults = formatter.format([])
    out: str = format_results.content
    assert out == dedent(f"""\
                        {base_dir.resolve()}/ [0]
                        └── src/main.py [1]
                        """)


def test_format_highlights_keywords_case_insensitive(tmp_path: Path) -> None:
    base_dir: Path = tmp_path / "repo"
    base_dir.mkdir()
    p: Path = __touch(base_dir / "src" / "MyFile.PY")
    formatter: RichTreeFormatter = RichTreeFormatter(base_dir, [p])
    format_results: FormatResults = formatter.format([Keyword("myfile")])
    red_start: str = "\033[91m"
    red_end: str = "\033[0m"
    out: str = format_results.content
    assert out == dedent(f"""\
                        {base_dir.resolve()}/ [0]
                        └── src/{red_start}MyFile{red_end}.PY [1]
                        """)


def test_format_compacts_single_child_directories(tmp_path: Path) -> None:
    base_dir: Path = tmp_path / "repo"
    base_dir.mkdir()
    # Create a chain: base/a/b/c.txt (each dir has exactly one child)
    leaf: Path = __touch(base_dir / "a" / "b" / "c.txt")
    formatter: RichTreeFormatter = RichTreeFormatter(base_dir, [leaf])
    format_results: FormatResults = formatter.format([])
    out: str = format_results.content
    assert out == dedent(f"""\
                        {base_dir.resolve()}/ [0]
                        └── a/b/c.txt [1]
                        """)


def test_format_does_not_compact_when_directory_has_multiple_children(tmp_path: Path) -> None:
    base_dir: Path = tmp_path / "repo"
    base_dir.mkdir()
    p1: Path = __touch(base_dir / "a" / "b1" / "c1.txt")
    p2: Path = __touch(base_dir / "a" / "b2" / "c2.txt")
    formatter: RichTreeFormatter = RichTreeFormatter(base_dir, [p1, p2])
    format_results: FormatResults = formatter.format([])
    out: str = format_results.content
    assert out == dedent(f"""\
                        {base_dir.resolve()}/ [0]
                        └── a/ [1]
                            ├── b1/c1.txt [2]
                            └── b2/c2.txt [3]
                        """)


def test_format_trailing_slash_for_directories(tmp_path: Path) -> None:
    base_dir: Path = tmp_path / "repo"
    base_dir.mkdir()
    # Select a directory itself as a "result" (not only files)
    d: Path = base_dir / "docs"
    d.mkdir(parents=True, exist_ok=True)
    formatter: RichTreeFormatter = RichTreeFormatter(base_dir, [d])
    format_results: FormatResults = formatter.format([])
    out: str = format_results.content
    assert out == dedent(f"""\
                        {base_dir.resolve()}/ [0]
                        └── docs/ [1]
                        """)


def test_format_multiple_keywords_highlighted(tmp_path: Path) -> None:
    base_dir: Path = tmp_path / "repo"
    base_dir.mkdir()
    p: Path = __touch(base_dir / "src" / "parser_tokenizer.py")
    formatter: RichTreeFormatter = RichTreeFormatter(base_dir, [p])
    format_results: FormatResults = formatter.format([Keyword("parser"), Keyword("token")])
    red_start: str = "\033[91m"
    red_end: str = "\033[0m"
    out: str = format_results.content
    assert out == dedent(f"""\
                        {base_dir.resolve()}/ [0]
                        └── src/{red_start}parser{red_end}_{red_start}token{red_end}izer.py [1]
                        """)


@pytest.mark.parametrize(
    "keywords,text,expected",
    [
        ([], "abc", "abc"),
        ([Keyword("a")], "abc", "\033[91ma\033[0mbc"),
        ([Keyword("ABC")], "abc", "\033[91mabc\033[0m"),
        ([Keyword("a"), Keyword("b")], "abc", "\033[91ma\033[0m\033[91mb\033[0mc"),
    ],
)
def test_highlight_word_direct(tmp_path: Path, keywords: list[Keyword], text: str, expected: str) -> None:
    # Access the static method via name-mangling (private by convention).
    got = RichTreeFormatter._RichTreeFormatter__highlight_word(text, keywords)  # type: ignore[attr-defined]
    assert got == expected


def __touch(p: Path) -> Path:
    p.parent.mkdir(parents=True, exist_ok=True)
    p.write_text("x", encoding="utf-8")
    return p

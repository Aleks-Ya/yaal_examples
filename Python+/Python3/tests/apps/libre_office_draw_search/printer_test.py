from pathlib import Path
from textwrap import dedent

from apps.libre_office_draw_search.data_types import FodgPath, SearchResults, SearchResult, FileName, PageName, \
    FolderName
from apps.libre_office_draw_search.printer import Printer


def test_format_keywords():
    act_str: str = Printer.format_keywords(["keyword1", "keyword2"])
    exp_str: str = "Keywords: ['keyword1', 'keyword2']"
    assert act_str == exp_str


def test_format_draw_files(root_dir: Path):
    act_str: str = Printer.format_draw_files([
        FodgPath(root_dir / 'dir1' / 'file1.fodg'),
        FodgPath(root_dir / 'dir1' / 'file2.fodg')
    ])
    exp_str: str = "Draw files: 2"
    assert act_str == exp_str


def test_format_result(root_dir: Path):
    search_results: SearchResults = SearchResults([
        SearchResult(1, FodgPath(root_dir / 'dir1' / 'file1.fodg'), [FolderName('dir1')], [FileName("file1.fodg")],
                     [PageName("Page 1")], []),
        SearchResult(2, FodgPath(root_dir / 'dir1' / 'file2.fodg'), [], [FileName("file2.fodg")],
                     [PageName("Page 1"), PageName("Page 2")], [])
    ], 3, 0, 2)
    printer: Printer = Printer(root_dir)
    act_formatted_results: str = printer.format_results(search_results)
    exp_formatted_results: str = dedent(
        """\
        Extracted pages: 3
        Extracted texts: 0
        Matched files: 2
        
        1 'file1.fodg' in 'dir1':
        Folder: 'dir1'
        Filename: 'file1.fodg'
        Pages 1: 'Page 1'
        
        2 'file2.fodg' in 'dir1':
        Filename: 'file2.fodg'
        Pages 2: 'Page 1', 'Page 2'
        """)
    assert act_formatted_results == exp_formatted_results

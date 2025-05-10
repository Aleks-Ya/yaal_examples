import sys
from pathlib import Path

path_dir: Path = Path(__file__).parent.parent.parent
sys.path.append(str(path_dir))

from apps.libre_office_draw_search.file_discoverer import FileDiscoverer
from apps.libre_office_draw_search.ranker import Ranker
from apps.libre_office_draw_search.data_types import FodgPath, SearchResults
from apps.libre_office_draw_search.opener import Opener
from apps.libre_office_draw_search.printer import Printer
from apps.libre_office_draw_search.searcher import Searcher

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python draw_find.py <keyword>")
        exit(1)

    root_dir: Path = Path('/home/aleks/DocsVault/LibreOfficeDraw')
    if not root_dir.exists():
        print(f"Directory does not exist: '{root_dir}'")
        exit(1)

    printer: Printer = Printer(root_dir)

    keyword_args: list[str] = sys.argv[1:]
    print(printer.format_keywords(keyword_args))

    draw_files: list[FodgPath] = FileDiscoverer.find_draw_files(root_dir)
    print(printer.format_draw_files(draw_files))

    searcher: Searcher = Searcher(root_dir)
    search_results: SearchResults = searcher.search(draw_files, keyword_args)
    ranked_search_results: SearchResults = Ranker.rank_results(search_results)
    results_str: str = printer.format_results(ranked_search_results)
    print(results_str)
    Opener.open_result(ranked_search_results)

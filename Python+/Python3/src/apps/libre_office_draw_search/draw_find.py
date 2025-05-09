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

    keyword_args: list[str] = sys.argv[1:]
    print(f"Keyword: '{keyword_args}'")

    draw_files: list[FodgPath] = FileDiscoverer.find_draw_files(root_dir)
    print(f"Draw files: {len(draw_files)}")

    search_results: SearchResults = Searcher.search(draw_files, keyword_args)
    print(f"Extracted pages: {search_results.pages_count}")
    print(f"Extracted texts: {search_results.texts_count}")
    print(f"Matched files: {search_results.matches_count}")
    print()
    ranked_search_results: SearchResults = Ranker.rank_results(search_results)
    Printer.print_results(ranked_search_results)
    Opener.open_result(ranked_search_results)

import sys
import time
from pathlib import Path

path_dir: Path = Path(__file__).parent.parent.parent
sys.path.append(str(path_dir))

from apps.yaal_examples_search.clipboard import Clipboard
from apps.yaal_examples_search.data_types import Keyword, Keywords
from apps.yaal_examples_search.formatter.rich_tree_formatter import RichTreeFormatter, FormatResults
from apps.yaal_examples_search.searcher.path_filter import PathFilter
from apps.yaal_examples_search.searcher.dir_reader import DirReader
from apps.yaal_examples_search.searcher.searcher import Searcher

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python examples_search.py <keyword>")
        exit(1)

    base_dir: Path = Path.home() / "pr" / "home" / "yaal_examples"
    if not base_dir.exists():
        print(f"Directory does not exist: '{base_dir}'")
        exit(1)

    keyword_args: list[str] = sys.argv[1:]
    print(f"Keywords: {keyword_args}")
    keywords: Keywords = Keywords([Keyword(keyword) for keyword in keyword_args])

    all_files_start: float = time.perf_counter()
    dir_reader: DirReader = DirReader(base_dir)
    all_files: list[Path] = dir_reader.get_all_files()
    all_files_elapsed: float = time.perf_counter() - all_files_start
    print(f'All files: {len(all_files)} ({all_files_elapsed:.3f}s)')

    filter_start: float = time.perf_counter()
    path_filter: PathFilter = PathFilter(base_dir)
    filtered_files: list[Path] = path_filter.filter(all_files)
    filter_elapsed: float = time.perf_counter() - filter_start
    print(f'Filtered files: {len(filtered_files)} ({filter_elapsed:.3f}s)')

    search_start: float = time.perf_counter()
    searcher: Searcher = Searcher(filtered_files)
    found_paths: set[Path] = searcher.search(keywords)
    sorted_paths: list[Path] = sorted(found_paths)
    search_elapsed: float = time.perf_counter() - search_start
    print(f'Match files: {len(sorted_paths)} ({search_elapsed:.3f}s)')

    formatter_start: float = time.perf_counter()
    print()
    formatter: RichTreeFormatter = RichTreeFormatter(base_dir, sorted_paths)
    format_results: FormatResults = formatter.format(keywords)
    content: str = format_results.content
    formatter_elapsed: float = time.perf_counter() - formatter_start
    print(content)
    print(f"Format: {formatter_elapsed:.3f}s")

    total_elapsed: float = time.perf_counter() - all_files_start
    print(f"Total: {total_elapsed:.3f}s")

    clipboard: Clipboard = Clipboard()
    clipboard.copy_path_to_clipboard(format_results)
    print()

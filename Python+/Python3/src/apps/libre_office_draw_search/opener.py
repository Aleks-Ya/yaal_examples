import os
import platform
import subprocess

from apps.libre_office_draw_search.data_types import FodgPath, SearchResult, SearchResults


class Opener:

    @staticmethod
    def open_result(search_results: SearchResults) -> None:
        if search_results.matches_count == 0:
            return
        while True:
            try:
                selected_rank: str = input("Enter the rank to open (or press Enter to exit): ")
                if selected_rank.isdigit():
                    rank: int = int(selected_rank)
                    matching_result: SearchResult = next(
                        (result for result in search_results.results if result.rank == rank), None)
                    if matching_result:
                        print(f"Opening file: {matching_result.draw_file}")
                        Opener.__open_file_in_default_app(matching_result.draw_file)
                    else:
                        print("Invalid rank selected.")
                else:
                    break
            except KeyboardInterrupt:
                print()
                break

    @staticmethod
    def __open_file_in_default_app(file_path: FodgPath) -> None:
        if platform.system() == 'Darwin':  # macOS
            subprocess.run(['open', file_path])
        elif platform.system() == 'Windows':  # Windows
            os.startfile(file_path)
        else:  # Linux and other Unix-like systems
            subprocess.run(['xdg-open', file_path])

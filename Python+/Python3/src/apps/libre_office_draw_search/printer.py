from pathlib import Path

from apps.libre_office_draw_search.data_types import SearchResults, Text, FodgPath


class Printer:
    def __init__(self, root_dir: Path):
        self.__root_dir: Path = root_dir

    @staticmethod
    def format_keywords(keywords: list[str]) -> str:
        return f"Keywords: {keywords}"

    @staticmethod
    def format_draw_files(draw_files: list[FodgPath]) -> str:
        return f"Draw files: {len(draw_files)}"

    def format_results(self, search_results: SearchResults) -> str:
        output: list[str] = [
            f"Extracted pages: {search_results.pages_count}",
            f"Extracted texts: {search_results.texts_count}",
            f"Matched files: {search_results.matches_count}",
            ""
        ]
        for result in search_results.results:
            if result.is_found():
                parent_relative: Path = result.draw_file.parent.relative_to(self.__root_dir)
                output.append(
                    f"{result.rank} '{result.draw_file.name}' in '{parent_relative}':")
                if result.are_folder_names_found():
                    output.append(f"Folder: '{parent_relative}'")
                if result.are_filenames_found():
                    output.append(f"Filename: '{result.draw_file.name}'")
                if result.are_pages_found():
                    pages_str: str = ", ".join(sorted([f"'{page}'" for page in set(result.page_names)]))
                    output.append(f"Pages {len(result.page_names)}: {pages_str}")
                if result.are_texts_found():
                    texts_set: set[Text] = set(result.texts)
                    texts_str: str = ", ".join(sorted([f"'{text}'" for text in texts_set]))
                    output.append(f"Texts {len(texts_set)}: {texts_str}")
                output.append("")
        return "\n".join(output)

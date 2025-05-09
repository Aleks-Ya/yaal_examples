from apps.libre_office_draw_search.data_types import SearchResults


class Printer:

    @staticmethod
    def print_results(search_results: SearchResults) -> None:
        for result in search_results.results:
            if result.found():
                print(f"{result.rank} {result.draw_file.name}:")
                if result.found_filename():
                    print(f"Filename: {result.draw_file.name}")
                if result.found_pages():
                    pages_str: str = ", ".join([f"'{page}'" for page in set(result.page_names)])
                    print(f"Pages {len(result.page_names)}: {pages_str}")
                if result.found_texts():
                    texts_str: str = ", ".join([f"'{text}'" for text in set(result.texts)])
                    print(f"Texts {len(result.texts)}: {texts_str}")
                print(result.draw_file)
                print()

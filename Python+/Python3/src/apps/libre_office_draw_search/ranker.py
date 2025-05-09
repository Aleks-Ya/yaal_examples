from apps.libre_office_draw_search.data_types import SearchResult, SearchResults


class Ranker:

    @staticmethod
    def rank_results(search_results: SearchResults) -> SearchResults:
        sorted_search_results: list[SearchResult] = sorted(search_results.results, key=Ranker.__rank, reverse=True)
        for result in sorted_search_results:
            result.rank = sorted_search_results.index(result) + 1
        return SearchResults(sorted_search_results, search_results.pages_count, search_results.texts_count,
                             search_results.matches_count)

    @staticmethod
    def __rank(result: SearchResult) -> int:
        filename_weight: int = len(result.file_names) * 100
        pages_weight: int = len(result.page_names) * 5
        texts_weight: int = len(result.texts)
        return filename_weight + pages_weight + texts_weight

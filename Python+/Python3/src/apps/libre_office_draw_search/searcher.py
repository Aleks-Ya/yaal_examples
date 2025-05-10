import xml.etree.ElementTree as ET
from itertools import chain
from pathlib import Path

from apps.libre_office_draw_search.data_types import FodgPath, FileName, PageName, Text, SearchResult, SearchResults, \
    FolderName
from apps.libre_office_draw_search.fodg_parser import FodgFileData, FodgParser


class Searcher:
    def __init__(self, root_dir: Path):
        self.__root_dir: Path = root_dir

    def search(self, files: list[FodgPath], keywords: list[str]) -> SearchResults:
        datas: dict[FodgPath, FodgFileData] = {file: FodgParser.parse(file) for file in files}
        pages_count: int = sum(len(data.page_names) for data in datas.values())
        texts_count: int = sum(len(data.texts) for data in datas.values())

        keyword_args_nested: list[list[str]] = [keyword_arg.lower().split() for keyword_arg in keywords]
        keyword_list: list[str] = list(chain.from_iterable(keyword_args_nested))
        results: list[SearchResult] = []
        for file, data in datas.items():
            folder_names: list[FolderName] = self.__find_folder_names(keyword_list, file)
            file_names: list[FileName] = Searcher.__find_filenames(keyword_list, file)
            page_names: list[PageName] = Searcher.__find_pages(keyword_list, data)
            texts: list[Text] = Searcher.__find_texts(keyword_list, data)
            results.append(SearchResult(0, file, folder_names, file_names, page_names, texts))
        matches_count: int = len([result for result in results if result.is_found()])

        return SearchResults(results, pages_count, texts_count, matches_count)

    @staticmethod
    def __get_namespaces(xml_file_path: FodgPath) -> dict[str, str]:
        namespaces: dict[str, str] = {}
        for event, elem in ET.iterparse(xml_file_path, events=['start-ns']):
            prefix, uri = elem
            namespaces[prefix] = uri
        return namespaces

    def __find_folder_names(self, keywords: list[str], file: FodgPath) -> list[FolderName]:
        matches: list[FolderName] = list[FolderName]()
        for keyword in keywords:
            for parent_folder in file.relative_to(self.__root_dir).parents:
                if keyword in parent_folder.name.lower():
                    matches.append(FolderName(parent_folder.name))
        return matches

    @staticmethod
    def __find_filenames(keywords: list[str], file: FodgPath) -> list[FileName]:
        matches: list[FileName] = list[FileName]()
        for keyword in keywords:
            if keyword in file.name.lower():
                matches.append(FileName(file.name))
        return matches

    @staticmethod
    def __find_pages(keywords: list[str], data: FodgFileData) -> list[PageName]:
        matches: list[PageName] = list[PageName]()
        for keyword in keywords:
            for page_name in data.page_names:
                if keyword in page_name.lower():
                    matches.append(PageName(page_name))
        return matches

    @staticmethod
    def __find_texts(keywords: list[str], data: FodgFileData) -> list[Text]:
        matches: list[Text] = list()
        for keyword in keywords:
            for text in data.texts:
                if keyword in text.lower():
                    matches.append(text)
        return matches

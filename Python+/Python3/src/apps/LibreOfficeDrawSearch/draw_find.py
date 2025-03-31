import os
import platform
import subprocess
import xml.etree.ElementTree as ET
from dataclasses import dataclass
from pathlib import Path
from xml.etree.ElementTree import Element, ElementTree
import sys
from itertools import chain

from fodg_parser import Data, FodgParser


def __find_draw_files(root_dir: Path) -> list[Path]:
    return list(root_dir.glob('**/*.fodg'))


def __extract_texts_from_file(xml_file: Path) -> list[str]:
    tree: ElementTree = ET.parse(xml_file)
    root: Element = tree.getroot()
    namespaces: dict[str, str] = __get_namespaces(xml_file)
    elements: list[Element] = root.findall('.//text:p', namespaces)
    return [element.text for element in elements if element.text is not None]


def __get_namespaces(xml_file_path: Path) -> dict[str, str]:
    namespaces: dict[str, str] = {}
    for event, elem in ET.iterparse(xml_file_path, events=['start-ns']):
        prefix, uri = elem
        namespaces[prefix] = uri
    return namespaces


def __contains_any(string: str, keywords: list[str]) -> bool:
    return any(substring in string for substring in keywords)


def __find_filename(keywords: list[str], file: Path) -> list[str]:
    matches: list[str] = []
    for keyword in keywords:
        if keyword in file.name.lower():
            matches.append(file.name)
    return matches


def __find_pages(keywords: list[str], data: Data) -> list[str]:
    matches: list[str] = []
    for keyword in keywords:
        for page_name in data.page_names:
            if keyword in page_name.lower():
                matches.append(page_name)
    return matches


def __find_texts(keywords: list[str], data: Data) -> list[str]:
    matches: list[str] = []
    for keyword in keywords:
        for text in data.texts:
            if keyword in text.lower():
                matches.append(text)
    return matches


@dataclass
class Result:
    rank: int
    file: Path
    filenames: list[str]
    pages: list[str]
    texts: list[str]

    def found_filename(self) -> bool:
        return len(self.filenames) > 0

    def found_pages(self) -> bool:
        return len(self.pages) > 0

    def found_texts(self) -> bool:
        return len(self.texts) > 0

    def found(self) -> bool:
        return self.found_filename() or self.found_pages() or self.found_texts()


def __rank(result: Result) -> int:
    filename_weight: int = len(result.filenames) * 100
    pages_weight: int = len(result.pages) * 5
    texts_weight: int = len(result.texts)
    return filename_weight + pages_weight + texts_weight


def __rank_results(results: list[Result]) -> list[Result]:
    sorted_results: list[Result] = sorted(results, key=__rank, reverse=True)
    for result in sorted_results:
        result.rank = sorted_results.index(result) + 1
    return sorted_results


def __open_file_in_default_app(file_path: Path) -> None:
    if platform.system() == 'Darwin':  # macOS
        subprocess.run(['open', file_path])
    elif platform.system() == 'Windows':  # Windows
        os.startfile(file_path)
    else:  # Linux and other Unix-like systems
        subprocess.run(['xdg-open', file_path])


def __open_result(ranked_results: list[Result]):
    selected_rank: str = input("Enter the rank of the file to open (or press Enter to skip): ")
    if selected_rank.isdigit():
        rank: int = int(selected_rank)
        matching_result: Result = next((result for result in ranked_results if result.rank == rank), None)
        if matching_result:
            print(f"Opening file: {matching_result.file}")
            __open_file_in_default_app(matching_result.file)
        else:
            print("Invalid rank selected.")


def __print_results(ranked_results: list[Result]):
    for result in ranked_results:
        if result.found():
            print(f"{result.rank} {result.file.name}:")
            if result.found_filename():
                print(f"Filename: {result.file.name}")
            if result.found_pages():
                pages_str: str = ", ".join([f"'{page}'" for page in set(result.pages)])
                print(f"Pages {len(result.pages)}: {pages_str}")
            if result.found_texts():
                texts_str: str = ", ".join([f"'{text}'" for text in set(result.texts)])
                print(f"Texts {len(result.texts)}: {texts_str}")
            print(result.file)
            print()


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python draw_find.py <keyword>")
        exit(1)

    root_dir: Path = Path('/home/aleks/DocsVault/LibreOfficeDraw')
    if not root_dir.exists():
        print(f"Directory does not exist: '{root_dir}'")
        exit(1)

    files: list[Path] = __find_draw_files(root_dir)
    print(f"Draw files: {len(files)}")

    datas: dict[Path, Data] = {file: FodgParser.parse(file) for file in files}
    pages_count: int = sum(len(data.page_names) for data in datas.values())
    print(f"Extracted pages: {pages_count}")
    texts_count: int = sum(len(data.texts) for data in datas.values())
    print(f"Extracted texts: {texts_count}")

    keyword_args: list[str] = sys.argv[1:]
    print(f"Keyword: '{keyword_args}'")

    keyword_args_nested: list[list[str]] = [keyword_arg.lower().split() for keyword_arg in keyword_args]
    keywords: list[str] = list(chain.from_iterable(keyword_args_nested))
    results: list[Result] = []
    for file, data in datas.items():
        filenames: list[str] = __find_filename(keywords, file)
        pages: list[str] = __find_pages(keywords, data)
        texts: list[str] = __find_texts(keywords, data)
        results.append(Result(0, file, filenames, pages, texts))
    matches_count: int = len([result for result in results if result.found()])
    print(f"Matched files: {matches_count}")
    print()

    ranked_results: list[Result] = __rank_results(results)
    __print_results(ranked_results)
    __open_result(ranked_results)

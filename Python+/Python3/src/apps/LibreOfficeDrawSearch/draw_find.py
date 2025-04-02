import os
import platform
import subprocess
import xml.etree.ElementTree as ET
from pathlib import Path
import sys
from itertools import chain

from fodg_parser import FodgFileData, FodgParser
from data_types import Results, FodgPath, Text, PageName, FileName


def __find_draw_files(root_dir: Path) -> list[FodgPath]:
    return list(root_dir.glob('**/*.fodg'))


def __get_namespaces(xml_file_path: FodgPath) -> dict[str, str]:
    namespaces: dict[str, str] = {}
    for event, elem in ET.iterparse(xml_file_path, events=['start-ns']):
        prefix, uri = elem
        namespaces[prefix] = uri
    return namespaces


def __find_filename(keywords: list[str], file: FodgPath) -> list[FileName]:
    matches: list[FileName] = list[FileName]()
    for keyword in keywords:
        if keyword in file.name.lower():
            matches.append(FileName(file.name))
    return matches


def __find_pages(keywords: list[str], data: FodgFileData) -> list[PageName]:
    matches: list[PageName] = list[PageName]()
    for keyword in keywords:
        for page_name in data.page_names:
            if keyword in page_name.lower():
                matches.append(page_name)
    return matches


def __find_texts(keywords: list[str], data: FodgFileData) -> list[Text]:
    matches: list[Text] = list()
    for keyword in keywords:
        for text in data.texts:
            if keyword in text.lower():
                matches.append(text)
    return matches


def __rank(result: Results) -> int:
    filename_weight: int = len(result.file_names) * 100
    pages_weight: int = len(result.page_names) * 5
    texts_weight: int = len(result.texts)
    return filename_weight + pages_weight + texts_weight


def __rank_results(results: list[Results]) -> list[Results]:
    sorted_results: list[Results] = sorted(results, key=__rank, reverse=True)
    for result in sorted_results:
        result.rank = sorted_results.index(result) + 1
    return sorted_results


def __open_file_in_default_app(file_path: FodgPath) -> None:
    if platform.system() == 'Darwin':  # macOS
        subprocess.run(['open', file_path])
    elif platform.system() == 'Windows':  # Windows
        os.startfile(file_path)
    else:  # Linux and other Unix-like systems
        subprocess.run(['xdg-open', file_path])


def __open_result(ranked_results: list[Results]) -> None:
    while True:
        selected_rank: str = input("Enter the rank to open (or press Enter to exit): ")
        if selected_rank.isdigit():
            rank: int = int(selected_rank)
            matching_result: Results = next((result for result in ranked_results if result.rank == rank), None)
            if matching_result:
                print(f"Opening file: {matching_result.draw_file}")
                __open_file_in_default_app(matching_result.draw_file)
            else:
                print("Invalid rank selected.")
        else:
            break


def __print_results(ranked_results: list[Results]) -> None:
    for result in ranked_results:
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


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python draw_find.py <keyword>")
        exit(1)

    root_dir: Path = Path('/home/aleks/DocsVault/LibreOfficeDraw')
    if not root_dir.exists():
        print(f"Directory does not exist: '{root_dir}'")
        exit(1)

    files: list[FodgPath] = __find_draw_files(root_dir)
    print(f"Draw files: {len(files)}")

    datas: dict[FodgPath, FodgFileData] = {file: FodgParser.parse(file) for file in files}
    pages_count: int = sum(len(data.page_names) for data in datas.values())
    print(f"Extracted pages: {pages_count}")
    texts_count: int = sum(len(data.texts) for data in datas.values())
    print(f"Extracted texts: {texts_count}")

    keyword_args: list[str] = sys.argv[1:]
    print(f"Keyword: '{keyword_args}'")

    keyword_args_nested: list[list[str]] = [keyword_arg.lower().split() for keyword_arg in keyword_args]
    keywords: list[str] = list(chain.from_iterable(keyword_args_nested))
    results: list[Results] = []
    for file, data in datas.items():
        file_names: list[FileName] = __find_filename(keywords, file)
        page_names: list[PageName] = __find_pages(keywords, data)
        texts: list[Text] = __find_texts(keywords, data)
        results.append(Results(0, file, file_names, page_names, texts))
    matches_count: int = len([result for result in results if result.found()])
    print(f"Matched files: {matches_count}")
    print()

    ranked_results: list[Results] = __rank_results(results)
    __print_results(ranked_results)
    __open_result(ranked_results)

from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path
import re
from re import Pattern
from typing import NewType

from rich.console import Console
from rich.tree import Tree

from apps.yaal_examples_search.data_types import Keyword

FormatResultId = NewType("FormatResultId", int)


@dataclass(frozen=True)
class FormatResults:
    results: dict[FormatResultId, Path]
    content: str


class RichTreeFormatter:
    def __init__(self, base_dir: Path, paths: list[Path]):
        self.__base_dir = base_dir
        self.__paths = paths
        self.__path_numbers: dict[Path, int] = {}
        self.__number_counter: int = 0

    def format(self, keywords: list[Keyword]) -> FormatResults:
        result, number_to_path = self.__format_with_numbers(keywords)
        results_dict: dict[FormatResultId, Path] = {FormatResultId(num): path for num, path in number_to_path.items()}
        return FormatResults(results=results_dict, content=result)

    def __format_with_numbers(self, keywords: list[Keyword]) -> tuple[str, dict[int, Path]]:
        self.__path_numbers = {}
        self.__number_counter = 0

        base: Path = self.__base_dir.resolve()
        selected: list[Path] = [p.resolve() for p in self.__paths]
        included: set[Path] = self.__collect_paths_to_display(base, selected)

        def label(path: Path, compacted_name: str | None = None) -> str:
            row_number = self.__number_counter
            self.__number_counter += 1
            self.__path_numbers[path] = row_number

            if compacted_name:
                highlighted = self.__highlight_word(compacted_name, keywords)
                return f"{highlighted} [{row_number}]"
            name: str = path.name if path != base else str(base)
            if path.is_dir():
                highlighted = self.__highlight_word(f"{name}/", keywords)
                return f"{highlighted} [{row_number}]"
            highlighted = self.__highlight_word(name, keywords)
            return f"{highlighted} [{row_number}]"

        root: Tree = Tree(label(base), guide_style="dim")
        nodes: dict[Path, Tree] = {base: root}
        processed: set[Path] = {base}

        for path in sorted(included, key=lambda x: (len(x.relative_to(base).parts) if x != base else -1, str(x))):
            if path == base or path in processed:
                continue

            parent = path.parent
            if parent not in nodes:
                continue

            if self.__should_compact(parent, included) and parent != base:
                continue

            if self.__should_compact(path, included):
                final_path, compacted_name = self.__build_compacted_path(path, included)
                nodes[final_path] = nodes[parent].add(label(final_path, compacted_name))
                current = path
                while current != final_path:
                    processed.add(current)
                    children = [p for p in included if p.parent == current]
                    current = children[0]
                processed.add(final_path)
            else:
                nodes[path] = nodes[parent].add(label(path))
                processed.add(path)

        console: Console = Console(record=True, width=120)
        with console.capture() as capture:
            console.print(root)

        number_to_path: dict[int, Path] = {num: path for path, num in self.__path_numbers.items()}
        return capture.get(), number_to_path

    def __is_contained(self, path: Path) -> bool:
        for p in self.__paths:
            if path == p or path in p.parents:
                return True
        return False

    @staticmethod
    def __highlight_word(text: str, keywords: list[Keyword]) -> str:
        if not keywords:
            return text
        # ANSI color codes for red text
        red_start: str = "\033[91m"
        red_end: str = "\033[0m"
        result: str = text
        for keyword in keywords:
            pattern: Pattern[str] = re.compile(re.escape(keyword), re.IGNORECASE)
            result = pattern.sub(lambda m: f"{red_start}{m.group()}{red_end}", result)
        return result

    @staticmethod
    def __should_compact(path: Path, included: set[Path]) -> bool:
        if not path.is_dir():
            return False
        children = [p for p in included if p.parent == path]
        return len(children) == 1

    def __build_compacted_path(self, path: Path, included: set[Path]) -> tuple[Path, str]:
        parts = [path.name]
        current = path

        while self.__should_compact(current, included):
            children = [p for p in included if p.parent == current]
            current = children[0]
            parts.append(current.name)

        compacted_name = "/".join(parts)
        if current.is_dir():
            compacted_name += "/"

        return current, compacted_name

    @staticmethod
    def __collect_paths_to_display(base: Path, selected: list[Path]) -> set[Path]:
        included: set[Path] = {base}
        for p in selected:
            try:
                p.relative_to(base)
            except ValueError:
                continue

            included.add(p)
            for parent in p.parents:
                included.add(parent)
                if parent == base:
                    break
        return included

from __future__ import annotations

from pathlib import Path
import re

from rich.console import Console
from rich.tree import Tree


class RichTreeFormatter:
    def __init__(self, base_dir: Path, paths: list[Path]):
        self.__base_dir = base_dir
        self.__paths = paths

    def format(self, word: str) -> str:
        base = self.__base_dir.resolve()
        selected = [p.resolve() for p in self.__paths]

        # Build the exact set of paths we want to show:
        #   - each selected path
        #   - each selected path's parents up to (and including) base
        included: set[Path] = {base}
        for p in selected:
            try:
                p.relative_to(base)
            except ValueError:
                # Path is outside base_dir; ignore it (matches typical "rooted tree" expectations).
                continue

            included.add(p)
            for parent in p.parents:
                included.add(parent)
                if parent == base:
                    break

        def highlight_word(text: str, word: str) -> str:
            """Highlight all occurrences of word (case-insensitive) in text with red color."""
            if not word:
                return text
            # ANSI color codes for red text
            red_start = "\033[91m"
            red_end = "\033[0m"
            # Use regex to find and replace case-insensitive matches
            pattern = re.compile(re.escape(word), re.IGNORECASE)
            return pattern.sub(lambda m: f"{red_start}{m.group()}{red_end}", text)

        def label(path: Path, compacted_name: str | None = None) -> str:
            if compacted_name:
                return highlight_word(compacted_name, word)
            name = path.name if path != base else str(base)
            if path.is_dir():
                return highlight_word(f"{name}/", word)
            return highlight_word(name, word)

        root = Tree(label(base), guide_style="dim")
        nodes: dict[Path, Tree] = {base: root}
        processed: set[Path] = {base}

        # Create parent nodes before children by sorting by depth then name.
        for path in sorted(included, key=lambda x: (len(x.relative_to(base).parts) if x != base else -1, str(x))):
            if path == base or path in processed:
                continue

            parent = path.parent
            if parent not in nodes:
                # Shouldn't happen because we included parents, but keep it safe.
                continue

            # Check if we should compact this path
            if self.__should_compact(parent, included) and parent != base:
                # Parent will be compacted, skip for now
                continue

            # Check if this path should start a compacted sequence
            if self.__should_compact(path, included):
                final_path, compacted_name = self.__build_compacted_path(path, included)
                nodes[final_path] = nodes[parent].add(label(final_path, compacted_name))
                # Mark all intermediate paths as processed
                current = path
                while current != final_path:
                    processed.add(current)
                    children = [p for p in included if p.parent == current]
                    current = children[0]
                processed.add(final_path)
            else:
                nodes[path] = nodes[parent].add(label(path))
                processed.add(path)

        console = Console(record=True, width=120)
        with console.capture() as capture:
            console.print(root)
        return capture.get()

    def __is_contained(self, path: Path) -> bool:
        for p in self.__paths:
            if path == p or path in p.parents:
                return True
        return False

    def __should_compact(self, path: Path, included: set[Path]) -> bool:
        """Check if a directory should be compacted (has exactly one child in included set)."""
        if not path.is_dir():
            return False
        children = [p for p in included if p.parent == path]
        return len(children) == 1

    def __build_compacted_path(self, path: Path, included: set[Path]) -> tuple[Path, str]:
        """Build compacted path for directories with single children. Returns (final_path, compacted_name)."""
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

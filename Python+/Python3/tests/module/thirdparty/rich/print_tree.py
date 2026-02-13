from textwrap import dedent

from rich.console import Console
from rich.tree import Tree


def test_print_tree():
    tree: Tree = __create_tree()
    console: Console = Console()
    console.print(tree)


def test_capture_tree_to_string():
    tree: Tree = __create_tree()
    console: Console = Console()
    with console.capture() as capture:
        console.print(tree)
    tree_string: str = capture.get()
    assert tree_string == dedent('''\
                                Root
                                ├── Branch 1
                                │   ├── Leaf 1.1
                                │   └── Leaf 1.2
                                └── Branch 2
                                    └── Leaf 2.1
                                ''')


def test_export_tree_to_string():
    tree: Tree = __create_tree()
    console: Console = Console(record=True)
    console.print(tree)
    tree_string: str = console.export_text()
    assert tree_string == dedent('''\
                                Root
                                ├── Branch 1
                                │   ├── Leaf 1.1
                                │   └── Leaf 1.2
                                └── Branch 2
                                    └── Leaf 2.1
                                ''')


def __create_tree() -> Tree:
    tree: Tree = Tree("Root")
    branch1: Tree = tree.add("Branch 1")
    branch1.add("Leaf 1.1")
    branch1.add("Leaf 1.2")
    branch2: Tree = tree.add("Branch 2")
    branch2.add("Leaf 2.1")
    return tree

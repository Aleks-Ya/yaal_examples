from anytree import Node, RenderTree


def test_print_tree(tree: Node):
    print("")
    for pre, fill, node in RenderTree(tree):
        print("%s%s" % (pre, node.name))


def test_render_tree_to_string(tree: Node):
    act_tree: str = RenderTree(tree).by_attr()
    exp_str: str = (
        "Udo\n"
        "├── Marc\n"
        "│   └── Lian\n"
        "└── Dan\n"
        "    ├── Jet\n"
        "    ├── Jan\n"
        "    └── Joe"
    )
    assert act_tree == exp_str

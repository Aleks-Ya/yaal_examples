from anytree import Node, find_by_attr


def test_find_by_attr_found(tree: Node, marc: Node):
    node: Node | None = find_by_attr(tree, "Marc", "name")
    assert node == marc


def test_find_by_attr_not_found(tree: Node):
    node: Node | None = find_by_attr(tree, "AbsentName", "name")
    assert node is None

from anytree import Node


def test_node_equals(tree: Node, marc: Node, lian: Node, dan: Node, jet: Node, jan: Node, joe: Node):
    assert tree == tree
    assert marc == marc
    assert lian == lian
    assert dan == dan
    assert jet == jet
    assert jan == jan
    assert joe == joe


def test_list_of_nodes_equals(tree: Node, marc: Node, lian: Node, dan: Node, jet: Node, jan: Node, joe: Node):
    assert [tree, marc, lian, dan, jet, jan, joe] == [tree, marc, lian, dan, jet, jan, joe]

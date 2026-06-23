from anytree import Node
from anytree.exporter import DictExporter


def test_export_to_dict(tree: Node):
    exporter: DictExporter = DictExporter()
    tree: dict = exporter.export(tree)
    assert tree == {
        'name': 'Udo',
        'children': [
            {
                'name': 'Marc',
                'children': [{'name': 'Lian'}]
            },
            {
                'name': 'Dan',
                'children': [{'name': 'Jet'}, {'name': 'Jan'}, {'name': 'Joe'}]
            }
        ]
    }


def test_compare_trees(tree: Node):
    udo: Node = Node("Udo")
    marc: Node = Node("Marc", parent=udo)
    Node("Lian", parent=marc)
    dan: Node = Node("Dan", parent=udo)
    Node("Jet", parent=dan)
    Node("Jan", parent=dan)
    Node("Joe", parent=dan)

    exporter: DictExporter = DictExporter()
    act_tree: dict = exporter.export(tree)
    exp_tree: dict = exporter.export(udo)
    assert act_tree == exp_tree

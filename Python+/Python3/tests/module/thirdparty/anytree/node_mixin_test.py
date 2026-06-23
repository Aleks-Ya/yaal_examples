from anytree import NodeMixin, find_by_attr
from anytree.exporter import DictExporter


class Stl(NodeMixin):
    def __init__(self, style_name: str, parent=None) -> None:
        self.style_name = style_name
        self.parent = parent

    def get_style_name(self) -> str:
        return self.style_name


def test_custom_class_as_node():
    root: Stl = Stl("Graphic")
    red: Stl = Stl("Red", parent=root)
    green: Stl = Stl("Green", parent=root)
    assert DictExporter().export(root) == {'style_name': 'Graphic',
                                           'children': [{'style_name': 'Red'}, {'style_name': 'Green'}]}
    assert find_by_attr(root, "Red", "style_name") == red
    assert find_by_attr(root, "Green", "style_name") == green

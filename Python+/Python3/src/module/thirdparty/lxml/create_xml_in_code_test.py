from lxml import etree
from lxml.etree import Element, SubElement


def test_create_xml_structure():
    root: Element = Element("root")
    assert root.tag == "root"

    root.append(Element("child1"))
    SubElement(root, "child2")
    SubElement(root, "child3")

    xml_string: str = etree.tostring(root, pretty_print=True).decode()
    assert "<child1/>" in xml_string
    assert "<child2/>" in xml_string
    assert "<child3/>" in xml_string

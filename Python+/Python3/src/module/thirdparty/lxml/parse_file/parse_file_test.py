from typing import TextIO
from xml.etree.ElementTree import ElementTree, Element

from lxml import etree


def test_parse_xml_file():
    file: TextIO = open('data.xml')
    tree: ElementTree = etree.parse(file)
    root_element: Element = tree.getroot()
    assert root_element.tag == 'data'

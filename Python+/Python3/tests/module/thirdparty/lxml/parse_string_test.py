from xml.etree.ElementTree import Element

from lxml import etree


def test_parse_xml():
    some_xml_data: str = "<root>data</root>"
    assert some_xml_data.strip() != ""
    root: Element = etree.fromstring(some_xml_data)
    assert root.tag == 'root'

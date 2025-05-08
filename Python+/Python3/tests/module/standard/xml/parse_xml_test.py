import xml.etree.ElementTree as ET
from xml.etree.ElementTree import Element, ElementTree


def test_parse_xml_string():
    xml: str = """<root>
        <child>data</child>
    </root>"""
    root: Element = ET.fromstring(xml)
    texts: list[str] = [child.text for child in root]
    assert texts == ['data']


def test_parse_xml_file():
    tree: ElementTree = ET.parse("data.xml")
    root: Element = tree.getroot()
    texts: list[str] = [child.text for child in root]
    assert texts == ['data']


def test_find_all_tags():
    xml: str = """
    <root>
        <level1>
            <image>Image1</image>
        </level1>
        <image>Image2</image>
    </root>
    """
    root: Element = ET.fromstring(xml)
    image_tags: list[Element] = root.findall('.//image')
    texts: list[str] = [image.text for image in image_tags]
    assert texts == ['Image1', 'Image2']

from lxml import etree
from lxml.etree import ElementTree, Element


def test_xpath():
    xml: str = """
    <root>
        <nested name="abc">data_1</nested>
        <nested name="efg">data_2</nested>
    </root>
    """
    tree: ElementTree = etree.fromstring(xml)
    assert tree.tag == 'root'

    nested_nodes: list[Element] = tree.xpath("/root/nested")
    print(nested_nodes)

    nested_nodes: list[Element] = tree.xpath("/root/nested/text()")
    print(nested_nodes)

    # nested_nodes = root.findall(".")
    # print(nested_nodes)
    # assert nested_nodes == 'root'


def test_find_tag():
    xml: str = """
    <root>
        <nested>data_1</nested>
        <nested>data_2</nested>
        <details>
            <nested>data_3</nested>
        </details>
    </root>
    """
    tree: ElementTree = etree.fromstring(xml)
    elements: list[Element] = tree.xpath("//*[contains(local-name(), 'nested')]")
    texts: list[str] = [f"{element.tag}={element.text}" for element in elements]
    assert texts == ['nested=data_1', 'nested=data_2', 'nested=data_3']

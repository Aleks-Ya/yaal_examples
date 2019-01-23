from lxml import etree

some_xml_data = "<root>data</root>"
root = etree.fromstring(some_xml_data)
assert root.tag == 'root'

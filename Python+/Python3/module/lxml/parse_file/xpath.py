from lxml import etree

some_xml_data = """
<root>
    <nested name="abc">data_1</nested>
    <nested name="efg">data_2</nested>
</root>
"""
root = etree.fromstring(some_xml_data)
assert root.tag == 'root'

nested_nodes = root.xpath("/root/nested")
print(nested_nodes)

nested_nodes = root.xpath("/root/nested/text()")
print(nested_nodes)


# nested_nodes = root.xpath("//*[contains(local-name(), 'nested')]")
nested_nodes = root.xpath("//*[contains(local-name(), 'nested')]")
print(nested_nodes)


# nested_nodes = root.findall(".")
# print(nested_nodes)
# assert nested_nodes == 'root'


from lxml import etree
from lxml.etree import Element, SubElement

root = Element("root")
print(root.tag)
root.append(Element("child1"))
child2 = SubElement(root, "child2")
child3 = SubElement(root, "child3")
print(etree.tostring(root, pretty_print=True))

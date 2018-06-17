from lxml import etree

file = open('data.xml')

tree = etree.parse(file)
root_element = tree.getroot()

assert root_element.tag == 'data'

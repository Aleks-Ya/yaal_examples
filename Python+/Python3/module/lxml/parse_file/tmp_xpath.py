import re

file = open('/home/aleks/tmp/fedstat_31557.html')

text = file.read()

# matchObj = re.match(r'.*Последнее обновление данных</div>.*<p>(d{2}.d{2}.d{4})<p>.*', text, re.M | re.I)
# matchObj = re.match(r'[.\n]*человек[.\n]*', text, re.M | re.I)
# matchObj = re.match(r'[.\n]*8[.\n]*', text, re.M | re.I)
#
# if matchObj:
#     print("matchObj.group() : ", matchObj.group())
# else:
#     print("No match!!")


sub = re.search(r'Последнее обновление данных</div>\s*<p>(\d{2}.\d{2}.\d{4})</p>', text)
print(sub.group(0))
print(sub.group(1))

# root = etree.parse(file)

# nested_nodes = root.xpath("/root/nested")
# print(nested_nodes)
#
# nested_nodes = root.xpath("/root/nested/text()")
# print(nested_nodes)
#

# nested_nodes = root.xpath("//*[contains(local-name(), 'nested')]")
# nested_nodes = root.xpath("//*[contains(local-name(), 'p') and contains(text(), '05.04.2018')]")
# print(nested_nodes)


# nested_nodes = root.findall(".")
# print(nested_nodes)
# assert nested_nodes == 'root'

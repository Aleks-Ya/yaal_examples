# Search by a regular expression in Russian text
import re

line = "Кошки\n круче собак \nво много раз"

matchObj = re.match(r'(.*)^ круче (.*?) $.*', line, re.M | re.I | re.DEBUG)

if matchObj:
    print("matchObj.group() : ", matchObj.group())
    print("matchObj.group(1) : ", matchObj.group(1))
    print("matchObj.group(2) : ", matchObj.group(2))
else:
    print("No match!!")

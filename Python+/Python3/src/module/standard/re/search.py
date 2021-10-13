# String contains any symbol from another string?
import re

assert re.search(r'[bc]', 'acde')
assert re.search(r'[bc]', 'abde')
assert not re.search(r'[bc]', 'ade')

assert re.search(r'[(){}]', 'a(b')
assert re.search(r'[(){}*]', 'a*b')

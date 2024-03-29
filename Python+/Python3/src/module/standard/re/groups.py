# Extract regex groups
import re
from re import Match

line = "All cats are smarter than dogs"

matchObj: Match = re.match(r'.* (.*) are (.*?) .*', line, re.M | re.I)

if matchObj:
    assert matchObj.group() == 'All cats are smarter than dogs'
    assert matchObj.group(1) == 'cats'
    assert matchObj.group(2) == 'smarter'
else:
    raise AssertionError

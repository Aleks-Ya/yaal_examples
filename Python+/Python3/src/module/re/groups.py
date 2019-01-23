# Extract regex groups
import re

line = "Cats are smarter than dogs"

matchObj = re.match(r'(.*) are (.*?) .*', line, re.M | re.I)

if matchObj:
    assert matchObj.group() == 'Cats are smarter than dogs'
    assert matchObj.group(1) == 'Cats'
    assert matchObj.group(2) == 'smarter'
else:
    raise AssertionError

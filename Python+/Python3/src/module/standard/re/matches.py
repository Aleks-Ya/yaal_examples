# Is a string matches a regular expression
import re
from re import Pattern

line: str = "Cats are smarter than dogs"

# Separate pattern compilation
pattern1: Pattern = re.compile('(.*) are (.*?) .*')
assert pattern1.match(line)
pattern2: Pattern = re.compile('\d+')
assert not pattern2.match(line)

# One liner
assert re.match(r'(.*) are (.*?) .*', line)
assert not re.match(r'\d+', line)

# Is a string matches a regular expression
import re

line = "Cats are smarter than dogs"

# Separate pattern compilation
pattern1 = re.compile('(.*) are (.*?) .*')
assert pattern1.match(line)
pattern2 = re.compile('\d+')
assert not pattern2.match(line)


# One liner
assert re.match(r'(.*) are (.*?) .*', line)
assert not re.match(r'\d+', line)

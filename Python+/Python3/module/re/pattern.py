# Examine pattern
import re

# Square brackets
line = "Cats [are smarter] than dogs"
match = re.match(r'Cats \[(.*?)\] than dogs', line)
assert match
assert match.group(1) == "are smarter"

# Comma
line = "123,456"
match = re.match(r'\d{3},\d{3}', line)
assert match

# Comma in character group
line = "123,456"
match = re.match(r'\d{3}([,;])\d{3}', line)
assert match
assert match.group(1) == ","



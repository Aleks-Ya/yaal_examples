# Examine pattern
import re
from re import Match

# Square brackets
line: str = "Cats [are smarter] than dogs"
match: Match = re.match(r'Cats \[(.*?)\] than dogs', line)
assert match
assert match.group(1) == "are smarter"

# Comma
line: str = "123,456"
match: Match = re.match(r'\d{3},\d{3}', line)
assert match

# Comma in character group
line: str = "123,456"
match: Match = re.match(r'\d{3}([,;])\d{3}', line)
assert match
assert match.group(1) == ","

# Parenthesis
line: str = "Cats (are smarter) than dogs"
match: Match = re.match(r'Cats \((.*?)\) than dogs', line)
assert match
assert match.group(1) == "are smarter"

# Ignore case
line: str = "CATS (are smarter) than dogs"
match: Match = re.match(r'Cats \((.*?)\) than dogs', line, re.IGNORECASE)
assert match
assert match.group(1) == "are smarter"

# Replace substring found by regex
import re

# Replace several matches
orig_line: str = "Cats are smarter than smarter dogs"
replaced_line: str = re.sub(r'\s\w+ter\s', ' smaller ', orig_line)
assert replaced_line == 'Cats are smaller than smaller dogs'

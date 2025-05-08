import re
from re import Match


def test_extract_regex_groups():
    line: str = "All cats are smarter than dogs"
    match: Match[str] = re.match(r'.* (.*) are (.*?) .*', line, re.M | re.I)
    assert match
    assert match.group() == 'All cats are smarter than dogs'
    assert match.group(1) == 'cats'
    assert match.group(2) == 'smarter'

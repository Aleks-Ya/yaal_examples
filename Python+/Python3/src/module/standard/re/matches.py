import re
from re import Pattern

line: str = "Cats are smarter than dogs"


def test_is_string_matches_pattern_separate_pattern_compilation():
    pattern1: Pattern = re.compile('(.*) are (.*?) .*')
    assert pattern1.match(line)
    pattern2: Pattern = re.compile('\d+')
    assert not pattern2.match(line)


def test_is_string_matches_pattern_one_liner():
    assert re.match(r'(.*) are (.*?) .*', line)
    assert not re.match(r'\d+', line)

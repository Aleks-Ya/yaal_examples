import re
from re import Match


def test_square_brackets():
    line: str = "Cats [are smarter] than dogs"
    match: Match[str] = re.match(r'Cats \[(.*?)] than dogs', line)
    assert match
    assert match.group(1) == "are smarter"


def test_comma():
    line: str = "123,456"
    match: Match[str] = re.match(r'\d{3},\d{3}', line)
    assert match


def test_comma_in_character_group():
    line: str = "123,456"
    match: Match[str] = re.match(r'\d{3}([,;])\d{3}', line)
    assert match
    assert match.group(1) == ","


def test_parenthesis():
    line: str = "Cats (are smarter) than dogs"
    match: Match[str] = re.match(r'Cats \((.*?)\) than dogs', line)
    assert match
    assert match.group(1) == "are smarter"


def test_ignore_case():
    line: str = "CATS (are smarter) than dogs"
    match: Match[str] = re.match(r'Cats \((.*?)\) than dogs', line, re.IGNORECASE)
    assert match
    assert match.group(1) == "are smarter"

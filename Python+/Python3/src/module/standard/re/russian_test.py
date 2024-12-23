import re
from re import Match

import pytest


def test_in_russian_text_one_line():
    line: str = "Кошки круче собак во много раз"
    match: Match[str] = re.match(r'(.*) круче (.*?) .*', line, re.M | re.I)
    assert match
    assert match.group() == "Кошки круче собак во много раз"
    assert match.group(1) == "Кошки"
    assert match.group(2) == "собак"


@pytest.mark.skip(reason="not working yet")
def test_in_russian_text_multi_line():
    line: str = "Кошки\n круче собак \nво много раз"
    match: Match[str] = re.match(r'(.*)^ круче (.*?) $.*', line, re.M | re.I | re.DEBUG)
    assert match
    assert match.group() == "Кошки\n круче собак \nво много раз"
    assert match.group(1) == "Кошки"
    assert match.group(2) == "собак"

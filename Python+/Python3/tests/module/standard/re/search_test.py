import re
from re import Match


def test_contains_any_symbol_from_another_string():
    assert re.search(r'[bc]', 'acde')
    assert re.search(r'[bc]', 'abde')
    assert not re.search(r'[bc]', 'ade')

    assert re.search(r'[(){}]', 'a(b')
    assert re.search(r'[(){}*]', 'a*b')


def test_extract_substrings():
    s: str = "hexMD5('\024' + document.login.password.value + '\145\170\124\253\200\024\147\171\231\373\034\250\111\214\010\126');"
    pattern: str = "hexMD5\('(.*)' \+ document.login.password.value \+ '(.*)'\);"
    match: Match[str] = re.search(pattern, s)
    group1: str = match.group(1)
    group2: str = match.group(2)

    assert group1 == '\024'
    assert group2 == '\145\170\124\253\200\024\147\171\231\373\034\250\111\214\010\126'


def test_extract_single_match():
    s: str = "aaa bbb ccc aaa xxx ccc"
    pattern: str = "aaa (\\w*) ccc"
    match: Match[str] = re.search(pattern, s)
    group1: str = match.group(1)
    assert group1 == 'bbb'


def test_extract_many_matches():
    s: str = "aaa bbb ccc aaa xxx ccc"
    pattern: str = "aaa (\\w*) ccc"
    matches: list[str] = re.findall(pattern, s)
    assert matches == ['bbb', 'xxx']

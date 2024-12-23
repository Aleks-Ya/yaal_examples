# Replace substring

def test_replace_all_occurrences():
    s: str = 'abcb'
    u: str = s.replace("b", "x")
    assert u == 'axcx'


def test_replace_dollar():
    u: str = 'a$bc'.replace("$", "\\$")
    print(u)
    assert u == "a\\$bc"
    assert u == "a\$bc"

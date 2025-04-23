from pytimeparse import parse


def test_parse():
    t: int = parse('1 day, 14:20:16')
    assert t == 138016

from pytimeparse2 import parse


def test_parse_minutes():
    seconds: int = parse('1.2 minutes')
    assert seconds == 72


def test_parse_short():
    seconds: int = parse('1h30m')
    assert seconds == 5400

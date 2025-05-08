# Parse boolean from str

# Standard bool() function (DO NOT USE IT!)
def test_standard_bool():
    assert bool('True') is True
    assert bool('False') is True
    assert bool('') is False


# Correct parsing
def parse_bool(s: str) -> bool:
    return s.lower() in ("yes", "true", "t", "1")


def test_parse_bool():
    assert parse_bool('True') is True
    assert parse_bool('False') is False
    assert parse_bool('yes') is True
    assert parse_bool('no') is False
    assert parse_bool('t') is True
    assert parse_bool('f') is False
    assert parse_bool('1') is True
    assert parse_bool('0') is False
    assert parse_bool('') is False

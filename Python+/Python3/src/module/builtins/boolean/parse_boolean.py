# Parse boolean from str

# Standard bool() function (DO NOT USE IT!)
assert bool('True') is True
assert bool('False') is True
assert bool('') is False


# Correct parsing
def parse_bool(s: str) -> bool:
    return s.lower() in ("yes", "true", "t", "1")


assert parse_bool('True') is True
assert parse_bool('False') is True

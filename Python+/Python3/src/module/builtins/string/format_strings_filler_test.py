def test_ljust():
    name: str = "John"
    wide_name: str = name.ljust(20)
    assert wide_name == "John                "


def test_rjust():
    name: str = "John"
    wide_name: str = name.rjust(20)
    assert wide_name == "                John"

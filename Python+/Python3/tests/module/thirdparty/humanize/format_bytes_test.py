import humanize


def test_convert_bytes_to_strings():
    assert humanize.naturalsize(1_000_000) == "1.0 MB"
    assert humanize.naturalsize(1_000_000, binary=True) == "976.6 KiB"
    assert humanize.naturalsize(1_000_000, gnu=True) == "976.6K"

import pytest
import humanfriendly
from humanfriendly import InvalidSize


def test_parse_size():
    assert 100_000 == humanfriendly.parse_size("100 KB")
    assert 100_000 == humanfriendly.parse_size("100KB")
    assert 100 == humanfriendly.parse_size("100B")
    assert 100_000_000 == humanfriendly.parse_size("100MB")
    assert 100_000 == humanfriendly.parse_size("100kb")


def test_parse_size_empty():
    with pytest.raises(InvalidSize):
        humanfriendly.parse_size("")


def test_parse_size_invalid():
    with pytest.raises(InvalidSize):
        humanfriendly.parse_size("abc")


def test_format_size():
    assert "100 KB" == humanfriendly.format_size(100_000)
    assert "1 MB" == humanfriendly.format_size(1_000_000)
    assert "1.00 MB" == humanfriendly.format_size(1_000_000, keep_width=True)
    assert "1.1 MB" == humanfriendly.format_size(1_100_000)

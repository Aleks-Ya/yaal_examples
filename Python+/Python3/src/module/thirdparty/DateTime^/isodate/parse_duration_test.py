from isodate import parse_duration, Duration, ISO8601Error
from pytest import raises


def test_parse_date_time():
    duration: Duration = parse_duration("P3Y6M4DT12H30M5S")
    assert str(duration) == "3 years, 6 months, 4 days, 12:30:05"


def test_parse_time():
    duration: Duration = parse_duration("PT12H30M5S")
    assert str(duration) == "12:30:05"


def test_parse_date():
    duration: Duration = parse_duration("P3Y6M4D")
    assert str(duration) == "3 years, 6 months, 4 days, 0:00:00"


def test_parse_zero_date():
    duration: Duration = parse_duration("P0D")
    assert str(duration) == "0:00:00"


def test_parse_zero_time():
    duration: Duration = parse_duration("PT0S")
    assert str(duration) == "0:00:00"


def test_parse_empty():
    with raises(ISO8601Error):
        parse_duration("")

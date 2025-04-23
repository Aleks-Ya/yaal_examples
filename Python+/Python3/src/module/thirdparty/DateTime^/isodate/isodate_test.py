from isodate import parse_duration, Duration


def test_parse():
    t: Duration = parse_duration("P3Y6M4DT12H30M5S")
    assert str(t) == "3 years, 6 months, 4 days, 12:30:05"

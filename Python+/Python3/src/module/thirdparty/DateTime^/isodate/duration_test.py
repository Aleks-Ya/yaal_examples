from isodate import Duration


def test_create_duration():
    duration: Duration = Duration(years=3, months=6, days=4, hours=12, minutes=30, seconds=5)
    assert str(duration) == "3 years, 6 months, 4 days, 12:30:05"

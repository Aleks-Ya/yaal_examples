from datetime import timedelta


def test_timedelta_to_sec():
    td: timedelta = timedelta(hours=1, minutes=30, seconds=40)
    act_seconds: float = td.total_seconds()
    exp_seconds: float = 1 * 60 * 60 + 30 * 60 + 40
    assert act_seconds == exp_seconds


def test_days():
    td: timedelta = timedelta(weeks=1, days=2, hours=36, minutes=30, seconds=40)
    assert td.days == 7 + 2 + 1  # weeks + days + hours

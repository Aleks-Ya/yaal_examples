import datetime
from datetime import datetime, timedelta


def test_date_difference():
    date_time1: datetime = datetime(2018, 12, 25, 8, 25, 30)
    date_time2: datetime = datetime(2018, 12, 26, 9, 26, 31)
    diff: timedelta = date_time2 - date_time1
    assert diff == timedelta(days=1, seconds=3661)


def test_sum_timedeltas():
    td1: timedelta = timedelta(days=1)
    td2: timedelta = timedelta(days=2)
    td_sum: timedelta = td1 + td2
    assert td_sum == timedelta(days=3)


def test_accumulate_timedeltas():
    td: timedelta = timedelta(days=1)
    td += timedelta(days=2)
    td += timedelta(days=3)
    assert td == timedelta(days=6)


def test_zero_timedelta():
    td: timedelta = timedelta()
    assert td == timedelta(days=0)


def test_timedelta_to_sec():
    delta: timedelta = timedelta(hours=1, minutes=30, seconds=40)
    act_seconds: float = delta.total_seconds()
    exp_seconds: float = 1 * 60 * 60 + 30 * 60 + 40
    assert exp_seconds == act_seconds

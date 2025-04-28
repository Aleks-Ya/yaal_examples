import datetime
from datetime import datetime
from datetime import time, date


def test_time_comparison():
    time1: time = time(8, 25, 30)
    time2: time = time(9, 25, 30)
    assert time1 < time2

    time1 = time(8, 25, 30)
    time2 = time(8, 25, 30)
    assert time1 == time2


def test_date_comparison():
    date1: date = date(2018, 12, 25)
    date2: date = date(2019, 12, 25)
    assert date1 < date2

    date1 = date(2018, 12, 25)
    date2 = date(2018, 12, 25)
    assert date1 == date2


def test_datetime_comparison():
    date_time1: datetime = datetime(2018, 12, 25, 8, 25, 30)
    date_time2: datetime = datetime(2018, 12, 25, 8, 25, 31)
    assert date_time1 < date_time2

    date_time1 = datetime(2018, 12, 25, 8, 25, 30)
    date_time2 = datetime(2018, 12, 25, 8, 25, 30)
    assert date_time1 == date_time2

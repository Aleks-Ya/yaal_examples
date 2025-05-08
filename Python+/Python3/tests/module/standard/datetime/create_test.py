# Renamed the class to follow pytest conventions
from datetime import datetime, time, date


def test_time():
    t: time = time(8, 25, 30)
    assert str(t) == '08:25:30'


def test_date():
    d: date = date(2018, 12, 25)
    assert str(d) == '2018-12-25'


def test_date_time():
    date_time: datetime = datetime(2018, 12, 25, 8, 25, 30)
    assert str(date_time) == '2018-12-25 08:25:30'


def test_date_time_millis():
    date_time_millis: datetime = datetime(2018, 12, 25, 8, 25, 30, 500)
    assert str(date_time_millis) == '2018-12-25 08:25:30.000500'


def test_datetime_from_epoch_milliseconds():
    date_time: datetime = datetime.fromtimestamp(1682743727)
    assert str(date_time) == '2023-04-29 11:48:47'


def test_today():
    today: date = date.today()
    assert today is not None


def test_now_date_time():
    now_date_time: datetime = datetime.now()
    assert now_date_time is not None


def test_now_utc():
    now_utc: datetime = datetime.utcnow()
    assert now_utc is not None

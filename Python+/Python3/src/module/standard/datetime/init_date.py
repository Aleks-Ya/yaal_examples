# Renamed the class to follow pytest conventions
import datetime


def test_time():
    time = datetime.time(8, 25, 30)
    assert str(time) == '08:25:30'


def test_date():
    date = datetime.date(2018, 12, 25)
    assert str(date) == '2018-12-25'


def test_date_time():
    date_time = datetime.datetime(2018, 12, 25, 8, 25, 30)
    assert str(date_time) == '2018-12-25 08:25:30'


def test_date_time_millis():
    date_time_millis = datetime.datetime(2018, 12, 25, 8, 25, 30, 500)
    assert str(date_time_millis) == '2018-12-25 08:25:30.000500'


def test_time_delta():
    time_delta = datetime.timedelta(seconds=3)
    assert str(time_delta) == '0:00:03'


def test_empty_time_delta():
    empty_time_delta = datetime.timedelta()
    assert str(empty_time_delta) == '0:00:00'


def test_today():
    today = datetime.date.today()
    assert today is not None


def test_now_date_time():
    now_date_time = datetime.datetime.now()
    assert now_date_time is not None


def test_now_utc():
    now_utc = datetime.datetime.utcnow()
    assert now_utc is not None

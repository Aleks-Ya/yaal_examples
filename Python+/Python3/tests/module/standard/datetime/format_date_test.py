import datetime
from datetime import date, datetime, time


def test_format_date():
    d: date = date(2018, 12, 25)
    s: str = d.strftime('%d-%m-%Y')
    assert s == '25-12-2018'


def test_format_datetime():
    d: datetime = datetime(2018, 12, 25, 8, 25, 30)
    s: str = d.strftime('%d-%m-%Y %H:%M:%S')
    assert s == '25-12-2018 08:25:30'


def test_format_time():
    d: time = time(14, 45, 55)
    s: str = d.strftime('%Hh %Mm %Ss')
    assert s == '14h 45m 55s'

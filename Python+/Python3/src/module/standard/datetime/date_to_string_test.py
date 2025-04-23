import datetime
from datetime import date


def test_datetime_to_linux_timestamp():
    ts: str = str(datetime.datetime(2018, 12, 25, 8, 25, 30))
    assert ts == '2018-12-25 08:25:30'


def test_date_to_string():
    d: date = datetime.date(2018, 12, 25)
    s: str = str(d)
    assert s == '2018-12-25'


def test_date_to_iso_format():
    d: date = datetime.date(2022, 1, 25)
    s: str = d.isoformat()
    assert s == '2022-01-25'

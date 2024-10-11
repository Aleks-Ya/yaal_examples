import datetime


def test_format_date():
    d: datetime.date = datetime.date(2018, 12, 25)
    s: str = d.strftime('%d-%m-%Y')
    assert s == '25-12-2018'


def test_format_datetime():
    d: datetime = datetime.datetime(2018, 12, 25, 8, 25, 30)
    s: str = d.strftime('%d-%m-%Y %H:%M:%S')
    assert s == '25-12-2018 08:25:30'


def test_format_time():
    d: datetime.time = datetime.time(14, 45, 55)
    s: str = d.strftime('%Hh %Mm %Ss')
    assert s == '14h 45m 55s'

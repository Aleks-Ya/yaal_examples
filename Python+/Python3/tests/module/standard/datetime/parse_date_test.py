import datetime
from datetime import date, datetime

from pytest import raises


def test_to_datetime():
    datetime_object: datetime = datetime.strptime('Jun 1 2005  1:33PM', '%b %d %Y %I:%M%p')
    print(datetime_object)
    assert datetime_object == datetime(2005, 6, 1, 13, 33)


def test_to_date():
    date_object: date = datetime.strptime('Jun 1 2005  1:33PM', '%b %d %Y %I:%M%p').date()
    print(date_object)
    assert date_object == date(2005, 6, 1)


def test_parse_iso_datetime_format():
    datetime_object: datetime = datetime.fromisoformat('2022-06-20 23:40:50.123456')
    print(datetime_object)
    assert datetime_object == datetime(2022, 6, 20, 23, 40, 50, 123456)


def test_parse_iso_time_format():
    date_object: date = datetime.fromisoformat('2022-06-21').date()
    print(date_object)
    assert date_object == date(2022, 6, 21)


def test_bad_format_date():
    with raises(ValueError) as ex:
        datetime.strptime('2022-06-35', '%Y-%M-%d')
    assert str(ex.value) == 'unconverted data remains: 5'

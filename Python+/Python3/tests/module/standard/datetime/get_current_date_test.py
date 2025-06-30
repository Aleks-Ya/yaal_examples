import datetime
from datetime import date, time
from unittest.mock import patch


def test_current_date_time():
    exp_datetime = datetime.datetime(2024, 1, 1, 0, 0, 0)
    with patch('datetime.datetime') as datetime_mock:
        datetime_mock.now.return_value = exp_datetime
        act_datetime = datetime.datetime.now()
        assert act_datetime == exp_datetime


def test_current_date_time_no_ms():
    exp_datetime_no_ms = datetime.datetime(2024, 1, 1, 0, 0, 0)
    with patch('datetime.datetime') as datetime_mock:
        datetime_mock.now.return_value = exp_datetime_no_ms
        act_datetime_no_ms = datetime.datetime.now()
        act_datetime_no_ms = act_datetime_no_ms.replace(microsecond=0)
        assert act_datetime_no_ms == exp_datetime_no_ms


def test_current_date():
    exp_datetime = datetime.datetime(2024, 1, 1, 0, 0, 0)
    with patch('datetime.datetime') as datetime_mock:
        datetime_mock.now.return_value = exp_datetime
        exp_date: date = exp_datetime.date()
        act_date: date = datetime.datetime.now().date()
        assert act_date == exp_date


def test_current_year():
    exp_year: int = 2024
    exp_datetime = datetime.datetime(exp_year, 1, 1, 0, 0, 0)
    with patch('datetime.datetime') as datetime_mock:
        datetime_mock.now.return_value = exp_datetime
        act_year: int = datetime.datetime.now().date().year
        assert act_year == exp_year


def test_current_month():
    exp_month: int = 9
    exp_datetime = datetime.datetime(2025, exp_month, 1, 0, 0, 0)
    with patch('datetime.datetime') as datetime_mock:
        datetime_mock.now.return_value = exp_datetime
        act_month: int = datetime.datetime.now().date().month
        assert act_month == exp_month


def test_previous_month():
    exp_datetime = datetime.datetime(2025, 1, 1, 0, 0, 0)
    with patch('datetime.datetime') as datetime_mock:
        datetime_mock.now.return_value = exp_datetime
        previous_month: int = __previous_month()
        assert previous_month == 12


def test_current_time():
    exp_datetime = datetime.datetime(2024, 1, 1, 0, 0, 0)
    with patch('datetime.datetime') as datetime_mock:
        datetime_mock.now.return_value = exp_datetime
        exp_time: time = exp_datetime.time()
        act_time: time = datetime.datetime.now().time()
        assert act_time == exp_time


def __previous_month() -> int:
    today = datetime.datetime.now()
    first_day = today.replace(day=1)
    prev_month_last_day = first_day - datetime.timedelta(days=1)
    return prev_month_last_day.month

import datetime

import pytest


@pytest.fixture
def datetime_mock(mocker):
    return mocker.patch('datetime.datetime')


def test_current_date_time(datetime_mock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_date_time = datetime.datetime(2024, 1, 1, 0, 0, 0)
    actual_date_time = datetime.datetime.now()
    assert expected_date_time == actual_date_time


def test_current_date_time_no_ms(datetime_mock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_date_time_no_ms = datetime.datetime(2024, 1, 1, 0, 0, 0)
    actual_date_time_no_ms = datetime.datetime.now()
    actual_date_time_no_ms = actual_date_time_no_ms.replace(microsecond=0)
    assert expected_date_time_no_ms == actual_date_time_no_ms


def test_current_date(datetime_mock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_date = datetime.datetime(2024, 1, 1, 0, 0, 0).date()
    actual_date = datetime.datetime.now().date()
    assert expected_date == actual_date


def test_current_year(datetime_mock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_year = 2024
    actual_year = datetime.datetime.now().date().year
    assert expected_year == actual_year


def test_current_time(datetime_mock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_time = datetime.datetime(2024, 1, 1, 0, 0, 0).time()
    actual_time = datetime.datetime.now().time()
    assert expected_time == actual_time

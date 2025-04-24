import datetime
from datetime import date, time
from unittest.mock import MagicMock

import pytest
from pytest_mock import MockerFixture


@pytest.fixture
def datetime_mock(mocker: MockerFixture) -> MagicMock:
    return mocker.patch('datetime.datetime')


def test_current_date_time(datetime_mock: MagicMock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_date_time = datetime.datetime(2024, 1, 1, 0, 0, 0)
    actual_date_time = datetime.datetime.now()
    assert expected_date_time == actual_date_time


def test_current_date_time_no_ms(datetime_mock: MagicMock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_date_time_no_ms = datetime.datetime(2024, 1, 1, 0, 0, 0)
    actual_date_time_no_ms = datetime.datetime.now()
    actual_date_time_no_ms = actual_date_time_no_ms.replace(microsecond=0)
    assert expected_date_time_no_ms == actual_date_time_no_ms


def test_current_date(datetime_mock: MagicMock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_date: date = datetime.datetime(2024, 1, 1, 0, 0, 0).date()
    actual_date: date = datetime.datetime.now().date()
    assert actual_date == expected_date


def test_current_year(datetime_mock: MagicMock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_year: int = 2024
    actual_year: int = datetime.datetime.now().date().year
    assert actual_year == expected_year


def test_current_time(datetime_mock: MagicMock):
    datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
    expected_time: time = datetime.datetime(2024, 1, 1, 0, 0, 0).time()
    actual_time: time = datetime.datetime.now().time()
    assert actual_time == expected_time

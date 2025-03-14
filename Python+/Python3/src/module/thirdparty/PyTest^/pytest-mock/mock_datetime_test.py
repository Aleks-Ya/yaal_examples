import datetime

import pytest
from pytest_mock import MockerFixture


@pytest.fixture
def datetime_mock(mocker: MockerFixture):
    return mocker.patch('datetime.datetime')


def test_mock_datetime(datetime_mock):
    expected_date_time = datetime.datetime(2024, 1, 1, 0, 0, 0)
    datetime_mock.now.return_value = expected_date_time
    actual_date_time = datetime.datetime.now()
    assert expected_date_time == actual_date_time

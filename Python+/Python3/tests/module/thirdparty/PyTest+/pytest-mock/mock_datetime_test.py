import datetime
from unittest.mock import MagicMock

from pytest_mock import MockerFixture


def test_mock_datetime(mocker: MockerFixture):
    exp_datetime = datetime.datetime(2024, 1, 1, 0, 0, 0)
    now_mock: MagicMock = mocker.patch('datetime.datetime')
    now_mock.now.return_value = exp_datetime
    act_datetime = datetime.datetime.now()
    assert exp_datetime == act_datetime
    assert str(type(act_datetime)) == "<class 'datetime.datetime'>"

import datetime
from datetime import datetime
from datetime import date


def test_datetime_to_date():
    date_time: datetime = datetime(2018, 12, 25, 8, 25, 30)
    act_date: date = date_time.date()
    assert act_date == date(2018, 12, 25)


def test_epoch_seconds_to_datetime():
    epoch_seconds: int = 1630755882
    act_datetime: date = datetime.fromtimestamp(epoch_seconds)
    assert act_datetime == datetime(2021, 9, 4, 18, 44, 42)


def test_epoch_seconds_to_date():
    epoch_seconds: int = 1630755882
    act_date: date = date.fromtimestamp(epoch_seconds)
    assert act_date == date(2021, 9, 4)

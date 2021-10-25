import datetime

from freezegun import freeze_time


def test():
    assert datetime.datetime.now() != datetime.datetime(2012, 1, 14)
    with freeze_time("2012-01-14"):
        assert datetime.datetime.now() == datetime.datetime(2012, 1, 14)
    assert datetime.datetime.now() != datetime.datetime(2012, 1, 14)

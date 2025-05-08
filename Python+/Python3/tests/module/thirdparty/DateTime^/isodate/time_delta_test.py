import datetime
from datetime import timedelta

from isodate import Duration


def test_tdelta():
    duration: Duration = Duration(years=3, months=6, days=4, hours=12, minutes=30, seconds=5)
    delta: timedelta = duration.tdelta
    assert str(delta) == "4 days, 12:30:05"


# Years and month are not included in timedelta.
# See https://github.com/gweis/isodate/issues/44
def test_tdelta_month():
    duration: Duration = Duration(months=1)
    assert str(duration.tdelta) == "0:00:00"


def test_totimedelta():
    duration: Duration = Duration(years=3, months=6, days=4, hours=12, minutes=30, seconds=5)
    start: datetime = datetime.datetime(2018, 12, 25, 8, 25, 30)
    delta: timedelta = duration.totimedelta(start=start)
    assert str(delta) == "1282 days, 12:30:05"

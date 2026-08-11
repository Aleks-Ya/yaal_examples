from dateutil import tz
from dateutil.tz import tzlocal


def test_get_current_timezone():
    current_timezone: tzlocal = tz.tzlocal()
    print(current_timezone.tzname(None))

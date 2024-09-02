from dateutil import tz
from dateutil.tz import tzlocal

current_timezone: tzlocal = tz.tzlocal()
print(current_timezone.tzname(None))

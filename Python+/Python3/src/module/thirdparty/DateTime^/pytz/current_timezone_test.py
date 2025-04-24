from datetime import datetime, tzinfo

import pytz


def test_current_timezone():
    timezone: tzinfo = pytz.timezone('UTC')
    current_time: datetime = datetime.now(timezone)
    timezone_info: tzinfo = current_time.tzinfo
    print(timezone_info)

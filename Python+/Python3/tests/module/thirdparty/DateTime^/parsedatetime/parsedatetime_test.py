from time import struct_time

import parsedatetime
from parsedatetime import Calendar


def test_tomorrow():
    cal: Calendar = parsedatetime.Calendar()
    d: tuple[struct_time, int] = cal.parse("tomorrow")
    print(d)

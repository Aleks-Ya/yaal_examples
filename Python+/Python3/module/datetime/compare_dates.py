# Comparing dates and times
import datetime

# Time
time1 = datetime.time(8, 25, 30)
time2 = datetime.time(9, 25, 30)
assert time1 < time2

time1 = datetime.time(8, 25, 30)
time2 = datetime.time(8, 25, 30)
assert time1 == time2

# Date
date1 = datetime.date(2018, 12, 25)
date2 = datetime.date(2019, 12, 25)
assert date1 < date2

date1 = datetime.date(2018, 12, 25)
date2 = datetime.date(2018, 12, 25)
assert date1 == date2

# Datetime
date_time1 = datetime.datetime(2018, 12, 25, 8, 25, 30)
date_time2 = datetime.datetime(2018, 12, 25, 8, 25, 31)
assert date_time1 < date_time2

date_time1 = datetime.datetime(2018, 12, 25, 8, 25, 30)
date_time2 = datetime.datetime(2018, 12, 25, 8, 25, 30)
assert date_time1 == date_time2

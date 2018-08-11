# Interval between dates
import datetime

# Calculate difference between dates
date_time1 = datetime.datetime(2018, 12, 25, 8, 25, 30)
date_time2 = datetime.datetime(2018, 12, 26, 9, 26, 31)
diff = date_time2 - date_time1
assert diff == datetime.timedelta(days=1, hours=1, minutes=1, seconds=1)

# Sum timedeltas
td1 = datetime.timedelta(days=1)
td2 = datetime.timedelta(days=2)
td_sum = td1 + td2
assert td_sum == datetime.timedelta(days=3)

# Accumulate timedeltas
td = datetime.timedelta(days=1)
td += datetime.timedelta(days=2)
td += datetime.timedelta(days=3)
assert td == datetime.timedelta(days=6)

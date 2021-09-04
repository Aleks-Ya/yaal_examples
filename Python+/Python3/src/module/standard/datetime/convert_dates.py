# Convert date, time and datetime to each other
import datetime

# datetime to date
date_time: datetime = datetime.datetime(2018, 12, 25, 8, 25, 30)
act_date = date_time.date()
assert act_date == datetime.date(2018, 12, 25)

print("Datetime: ", date_time)
print("Date: ", act_date)

# epoch seconds to datetime
epoch_seconds: int = 1630755882
act_datetime: datetime = datetime.datetime.fromtimestamp(epoch_seconds)
assert act_datetime == datetime.datetime(2021, 9, 4, 14, 44, 42)

# epoch seconds to date
epoch_seconds: int = 1630755882
act_date: datetime.date = datetime.date.fromtimestamp(epoch_seconds)
assert act_date == datetime.date(2021, 9, 4)

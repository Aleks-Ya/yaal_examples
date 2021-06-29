# Convert date, time and datetime to each other
import datetime

# datetime to date
date_time = datetime.datetime(2018, 12, 25, 8, 25, 30)
date = date_time.date()
assert date == datetime.date(2018, 12, 25)

print("Datetime: ", date_time)
print("Date: ", date)


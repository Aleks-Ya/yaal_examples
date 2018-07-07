# Creating a date/time instance
import datetime

time = datetime.time(8, 25, 30)
print("Time: ", time)

date = datetime.date(2018, 12, 25)
print("Date: ", date)

date_time = datetime.datetime(2018, 12, 25, 8, 25, 30)
print("Datetime: ", date_time)

date_time_millis = datetime.datetime(2018, 12, 25, 8, 25, 30, 500)
print("Datetime with milliseconds: ", date_time_millis)

time_delta = datetime.timedelta(seconds=3)
print("Time delta: ", time_delta)

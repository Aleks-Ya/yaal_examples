# Creating a date/time instance
import datetime

time = datetime.time(8, 25, 30)
print("Time: ", time)

date = datetime.date(2018, 12, 25)
print("Date: ", date)

date_time = datetime.datetime(2018, 12, 25, 8, 25, 30)
print("Datetime: ", date_time)

date_time_millis = datetime.datetime(2018, 12, 25, 8, 25, 30, 500)
print("Datetime with microseconds: ", date_time_millis)

time_delta = datetime.timedelta(seconds=3)
print("Time delta: ", time_delta)

empty_time_delta = datetime.timedelta()
print("Empty time delta: ", empty_time_delta)

# Today
today = datetime.date.today()
print("Today: ", today)

now_date_time = datetime.datetime.now()
print(f"now_date_time: {now_date_time}")

now_utc = datetime.datetime.utcnow()
print(f"now_utc: {now_utc}")

# Epoch
now_epoch_seconds = int(datetime.datetime.now().timestamp())
print(f"now_epoch: {now_epoch_seconds}")

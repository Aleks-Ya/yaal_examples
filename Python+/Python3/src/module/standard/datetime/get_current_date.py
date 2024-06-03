# Obtaining the current date and time
import datetime

current_date_time: datetime.datetime = datetime.datetime.now()
print("Current datetime: ", current_date_time)

current_date_time_no_ms: datetime.datetime = datetime.datetime.now().replace(microsecond=0)
print("Current datetime (no microseconds): ", current_date_time_no_ms)

current_date: datetime.date = datetime.datetime.now().date()
print("Current date: ", current_date)

current_year: int = datetime.datetime.now().date().year
print("Current year: ", current_year)

current_time: datetime.time = datetime.datetime.now().time()
print("Current time: ", current_time)

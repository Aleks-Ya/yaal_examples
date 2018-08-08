# Obtaining the current date and time
import datetime

current_date_time = datetime.datetime.now()
print("Current datetime: ", current_date_time)

current_date = datetime.datetime.now().date()
print("Current date: ", current_date)

current_year = datetime.datetime.now().date().year
print("Current year: ", current_year)

current_time = datetime.datetime.now().time()
print("Current time: ", current_time)

# Convert a string to a date/time instance
import datetime

# To datetime
datetime_object = datetime.datetime.strptime('Jun 1 2005  1:33PM', '%b %d %Y %I:%M%p')
print(datetime_object)
assert datetime_object == datetime.datetime(2005, 6, 1, 13, 33)

# To date
date_object = datetime.datetime.strptime('Jun 1 2005  1:33PM', '%b %d %Y %I:%M%p').date()
print(date_object)
assert date_object == datetime.date(2005, 6, 1)

# Convert a string to a date/time instance
import datetime

# To datetime
datetime_object: datetime = datetime.datetime.strptime('Jun 1 2005  1:33PM', '%b %d %Y %I:%M%p')
print(datetime_object)
assert datetime_object == datetime.datetime(2005, 6, 1, 13, 33)

# To date
date_object: datetime.date = datetime.datetime.strptime('Jun 1 2005  1:33PM', '%b %d %Y %I:%M%p').date()
print(date_object)
assert date_object == datetime.date(2005, 6, 1)

# Parse ISO datetime format
datetime_object: datetime.date = datetime.datetime.fromisoformat('2022-06-20 23:40:50.123456')
print(datetime_object)
assert datetime_object == datetime.datetime(2022, 6, 20, 23, 40, 50, 123456)

# Parse ISO time format
date_object: datetime.date = datetime.datetime.fromisoformat('2022-06-21').date()
print(date_object)
assert date_object == datetime.date(2022, 6, 21)

# Bad format date
try:
    datetime.datetime.strptime('2022-06-35', '%Y-%M-%d')
    raise AssertionError('Exception should be thrown')
except ValueError as ex:
    assert ex.args[0] == 'unconverted data remains: 5'

# Convert a date to a string
import datetime

# To Linux timestamp
ts = str(datetime.datetime(2018, 12, 25, 8, 25, 30))
assert ts == '2018-12-25 08:25:30'

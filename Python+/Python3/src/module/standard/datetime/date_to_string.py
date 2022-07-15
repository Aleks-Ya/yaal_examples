# Convert a date to a string
import datetime

# To Linux timestamp
ts = str(datetime.datetime(2018, 12, 25, 8, 25, 30))
assert ts == '2018-12-25 08:25:30'

# date to string
d: datetime.date = datetime.date(2018, 12, 25)
s: str = str(d)
assert s == '2018-12-25'

# date to ISO format
d: datetime.date = datetime.date(2022, 1, 25)
s: str = d.isoformat()
assert s == '2022-01-25'

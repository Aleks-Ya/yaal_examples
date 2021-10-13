import datetime

# Format date
d: datetime.date = datetime.date(2018, 12, 25)
s: str = d.strftime('%d-%m-%Y')
assert s == '25-12-2018'

# Format datetime
d: datetime.datetime = datetime.datetime(2018, 12, 25, 8, 25, 30)
s: str = d.strftime('%d-%m-%Y %H:%M:%S')
assert s == '25-12-2018 08:25:30'

import datetime
from datetime import date, timedelta


def test_dates():
    today: date = datetime.date.today()
    print("Today: ", today)

    one_day: timedelta = datetime.timedelta(days=1)
    print("One day: ", one_day)

    yesterday: date = today - one_day
    print("Yesterday: ", yesterday)

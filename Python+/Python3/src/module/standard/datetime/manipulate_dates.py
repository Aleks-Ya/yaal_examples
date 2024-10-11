import datetime


def test_dates():
    today = datetime.date.today()
    print("Today: ", today)

    one_day = datetime.timedelta(days=1)
    print("One day: ", one_day)

    yesterday = today - one_day
    print("Yesterday: ", yesterday)

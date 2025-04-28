from datetime import timedelta


def test_str():
    assert str(timedelta(weeks=2)) == '14 days, 0:00:00'
    assert str(timedelta(days=2)) == '2 days, 0:00:00'
    assert str(timedelta(hours=15)) == '15:00:00'
    assert str(timedelta(minutes=15)) == '0:15:00'
    assert str(timedelta(seconds=3)) == '0:00:03'
    assert str(timedelta(milliseconds=5)) == '0:00:00.005000'
    assert str(timedelta(microseconds=5)) == '0:00:00.000005'
    assert str(timedelta()) == '0:00:00'


def test_repr():
    deltas: list[timedelta] = [
        timedelta(weeks=2),
        timedelta(days=2),
        timedelta(hours=15),
        timedelta(minutes=15),
        timedelta(seconds=3),
        timedelta(milliseconds=5),
        timedelta(microseconds=5),
        timedelta()
    ]
    assert str(deltas) == ('[datetime.timedelta(days=14), datetime.timedelta(days=2), '
                           'datetime.timedelta(seconds=54000), datetime.timedelta(seconds=900), '
                           'datetime.timedelta(seconds=3), datetime.timedelta(microseconds=5000), '
                           'datetime.timedelta(microseconds=5), datetime.timedelta(0)]')

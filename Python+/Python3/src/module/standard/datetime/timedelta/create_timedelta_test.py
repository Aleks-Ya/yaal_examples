from datetime import timedelta


def test_time_delta():
    td: timedelta = timedelta(seconds=3)
    assert str(td) == '0:00:03'


def test_empty_time_delta():
    td: timedelta = timedelta()
    assert str(td) == '0:00:00'
    assert td == timedelta(days=0)

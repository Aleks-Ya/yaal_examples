import time


def test_current_time_millis():
    current_time_millis: int = int(round(time.time() * 1000))
    print(current_time_millis)


def test_current_time_formatted():
    s: str = time.strftime('%H:%M:%S')
    print(s)

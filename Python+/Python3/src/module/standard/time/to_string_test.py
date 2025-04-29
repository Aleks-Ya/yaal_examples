import time


def test_current_time_formatted():
    s: str = time.strftime('%H:%M:%S')
    print(s)

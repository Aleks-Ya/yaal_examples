import time


def test_current_time():
    epoch_seconds: float = time.time()
    print(epoch_seconds)


def test_current_time_ns():
    epoch_nanoseconds: int = time.time_ns()
    print(epoch_nanoseconds)


def test_current_time_millis():
    current_time_millis: int = int(round(time.time() * 1000))
    print(current_time_millis)

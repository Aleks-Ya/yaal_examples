import timeit
from time import sleep


def test_function_as_string():
    start: float = timeit.default_timer()
    sleep(2)
    end: float = timeit.default_timer()
    execution_time_sec: float = end - start
    assert execution_time_sec >= 2

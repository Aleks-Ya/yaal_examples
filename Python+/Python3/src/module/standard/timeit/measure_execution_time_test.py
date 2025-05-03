import timeit
from time import sleep


def __method_under_test_with_parameters(duration: float):
    sleep(duration)


def test_measure_time():
    execution_time: float = timeit.timeit(_method_under_test, number=1)
    assert execution_time > 0.1


def test_measure_time_with_parameters_lambda():
    execution_time: float = timeit.timeit(
        lambda: __method_under_test_with_parameters(0.5), number=1)
    assert execution_time > 0.1


def test_measure_time_with_parameters_string():
    execution_time: float = timeit.timeit(
        '_method_under_test_with_parameters(0.5)', globals=globals(), number=1)
    assert execution_time > 0.1


def _method_under_test():
    sleep(0.5)

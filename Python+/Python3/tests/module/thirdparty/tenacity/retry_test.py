import datetime
from typing import Callable, Any

from tenacity import retry, stop_after_attempt, WrappedFn, wait_fixed, wait_exponential


class FailingTask:
    def __init__(self):
        self.__counter: int = 0
        print()

    def do_work(self):
        self.__counter += 1
        if self.__counter < 5:
            print(f"{datetime.datetime.now().replace(microsecond=0)} Fail on attempt: {self.__counter}")
            raise Exception("Try again")
        print(f"{datetime.datetime.now().replace(microsecond=0)} Success on attempt: {self.__counter}")


def test_retry_immediately():
    task: FailingTask = FailingTask()
    retrying: Callable[[WrappedFn], WrappedFn] = retry(stop=stop_after_attempt(6))
    my_function_with_retry: Callable[[], Any] = retrying(task.do_work)
    my_function_with_retry()


def test_retry_with_wait_interval():
    task: FailingTask = FailingTask()
    retrying: Callable[[WrappedFn], WrappedFn] = retry(stop=stop_after_attempt(6), wait=wait_fixed(1))
    my_function_with_retry: Callable[[], Any] = retrying(task.do_work)
    my_function_with_retry()


def test_retry_with_exponential_interval():
    task: FailingTask = FailingTask()
    retrying: Callable[[WrappedFn], WrappedFn] = retry(stop=stop_after_attempt(6),
                                                       wait=wait_exponential(multiplier=2, min=1, max=6))
    my_function_with_retry: Callable[[], Any] = retrying(task.do_work)
    my_function_with_retry()


def test_retry_oneliner():
    task: FailingTask = FailingTask()
    retry(stop=stop_after_attempt(6))(task.do_work)()

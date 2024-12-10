from typing import Callable, Any

from tenacity import retry, stop_after_attempt, WrappedFn, wait_fixed

counter: int = 0


def my_function():
    global counter
    counter += 1
    if counter < 3:
        raise Exception("Try again")


def test_retry_immediately():
    global counter
    counter = 0
    retrying: Callable[[WrappedFn], WrappedFn] = retry(stop=stop_after_attempt(5))
    my_function_with_retry: Callable[[], Any] = retrying(my_function)
    my_function_with_retry()


def test_retry_with_wait_interval():
    global counter
    counter = 0
    retrying: Callable[[WrappedFn], WrappedFn] = retry(stop=stop_after_attempt(5), wait=wait_fixed(1))
    my_function_with_retry: Callable[[], Any] = retrying(my_function)
    my_function_with_retry()


def test_retry_oneliner():
    global counter
    counter = 0
    retry(stop=stop_after_attempt(5))(my_function)()

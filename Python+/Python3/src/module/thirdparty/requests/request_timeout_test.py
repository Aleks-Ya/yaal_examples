import requests
from urllib3.exceptions import ReadTimeoutError


def test_request_timeout1():
    timeout_secs = 0.1
    try:
        requests.get("https://www.google.ru/search", params={'q': 'football', 'bih': 980}, timeout=timeout_secs)
        assert False
    except (ReadTimeoutError, requests.exceptions.ConnectionError) as ex:
        print("Timeout is expected here!")


def test_request_timeout2():
    timeout_secs = 0.1
    try:
        requests.get("https://www.google.ru/search", params={'q': 'football', 'bih': 980}, timeout=timeout_secs)
        assert False
    except ReadTimeoutError as ex:
        print("Timeout is expected here: ", ex)
    except requests.exceptions.ConnectionError as ex:
        print("Connection error is expected here: ", ex)

import datetime

import requests
from requests import Response
from requests.structures import CaseInsensitiveDict


def __send_request() -> Response:
    url: str = "https://api.github.com/repos/Aleks-Ya/yaal_examples/languages"
    return requests.request("GET", url)


def __response_to_str(response: Response) -> str:
    status_code: int = response.status_code
    headers: CaseInsensitiveDict[str] = response.headers
    retry_after: str = headers.get("retry-after")
    retry_limit_remaining: str = headers.get("x-ratelimit-remaining")
    retry_limit_reset: str = headers.get("x-ratelimit-reset")
    return str(f"Status={status_code}, "
               f"retry_after={retry_after}, "
               f"retry_limit_remaining={retry_limit_remaining}, "
               f"retry_limit_reset={retry_limit_reset}, "
               f"retry_limit_reset={datetime.datetime.fromtimestamp(int(retry_limit_reset))}")


def test_print_request():
    print()
    response: Response = __send_request()
    print(__response_to_str(response))


def test_send_10_requests():
    print()
    for i in range(10):
        response: Response = __send_request()
        print(f"Request {i + 1}: {__response_to_str(response)}")


def test_exceed_rate_limit():
    print()
    rate_limit: int = 60
    for i in range(rate_limit):
        response: Response = __send_request()
        if response.status_code != 200:
            print(f"Error: {__response_to_str(response)}")
            print(f"Body: {response.text}")
            break
        print(f"Request {i + 1}: {__response_to_str(response)}")

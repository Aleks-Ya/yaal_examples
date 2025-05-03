import requests
from pytest_check import raises
from requests import Response, HTTPError


def test_raise_for_status_200():
    response: Response = requests.get('https://httpbin.io/status/200')
    response.raise_for_status()


def test_raise_for_status_300():
    response: Response = requests.get('https://httpbin.io/status/300')
    response.raise_for_status()


def test_raise_for_status_400():
    response: Response = requests.get('https://httpbin.io/status/400')
    with raises(HTTPError):
        response.raise_for_status()


def test_raise_for_status_500():
    response: Response = requests.get('https://httpbin.io/status/500')
    with raises(HTTPError):
        response.raise_for_status()

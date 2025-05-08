import os
import tempfile
from http.client import HTTPMessage
from urllib import request


def test_download_to_given_file():
    url: str = 'https://www.python.org/static/img/python-logo.png'
    _, file = tempfile.mkstemp(suffix='.png')
    print(file)
    request.urlretrieve(url, file)
    assert os.path.getsize(file) > 0


def test_download_to_temp_file():
    url: str = 'https://www.python.org/static/img/python-logo.png'
    result: tuple[str, HTTPMessage] = request.urlretrieve(url)
    file: str = result[0]
    print(file)
    assert os.path.getsize(file) > 0

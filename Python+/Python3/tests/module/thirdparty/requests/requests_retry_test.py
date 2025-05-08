# Retry HTTP request
import requests
from requests import Session, Response
from requests.adapters import HTTPAdapter


def test_retry():
    s: Session = requests.Session()
    url: str = 'http://not-exists.ru'
    s.mount(url, HTTPAdapter(max_retries=10))

    response: Response = s.get(url)
    assert response.status_code == 200

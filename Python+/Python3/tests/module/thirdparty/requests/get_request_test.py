import requests
from requests import Response


def test_get_request():
    response: Response = requests.get('https://ya.ru/')
    assert response.status_code == 200
    assert len(response.content) > 0
    print('Content:\n' + response.content.decode('utf-8'))


def test_get_request_with_params():
    response: Response = requests.get("https://www.google.ru/search", params={'q': 'football', 'bih': 980})
    print(response.status_code, response.reason)
    print(response.text[:200] + '...')
    assert response.status_code == 200


def test_accept_content_type():
    headers: dict[str, str] = {'Accept': 'image/jpeg'}
    response: Response = requests.get('https://httpbin.io/image', headers=headers)
    assert response.status_code == 200
    assert len(response.content) > 0

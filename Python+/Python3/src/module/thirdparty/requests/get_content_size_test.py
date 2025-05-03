import requests
from requests import Response


def test_get_content_size():
    size: int = 500
    response: Response = requests.head(f'https://httpbin.io/bytes/{size}')
    assert response.status_code == 200
    print(response.headers)
    assert response.headers['Content-Length'] == str(size)

import requests
from requests import Response


def test_get_content_size():
    exp_size: int = 500
    response: Response = requests.head(f'https://httpbin.io/bytes/{exp_size}')
    assert response.status_code == 200
    print(response.headers)
    act_size_str: str = response.headers['Content-Length']
    act_size: int = int(act_size_str)
    assert act_size == exp_size

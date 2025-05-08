import requests
from requests import Response


def test_post_request_1():
    # To bugs.python.org
    r: Response = requests.post("http://bugs.python.org", data={'number': 12524, 'type': 'issue', 'action': 'show'})
    print(r.status_code, r.reason)
    print(r.text[:300] + '...')
    assert r.status_code == 200


def test_post_request_2():
    # To YandexDictionary
    text: str = 'green car'
    key: str = 'dict......'
    r: Response = requests.post("https://dictionary.yandex.net/api/v1/dicservice.json/lookup",
                                data={'lang': 'en-ru', 'text': text, 'key': key})
    print(r.status_code, r.reason)
    print(r.text)

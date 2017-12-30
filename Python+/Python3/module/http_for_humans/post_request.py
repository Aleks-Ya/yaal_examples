# Send POST request
import requests

# To bugs.python.org
r = requests.post("http://bugs.python.org", data={'number': 12524, 'type': 'issue', 'action': 'show'})
print(r.status_code, r.reason)
print(r.text[:300] + '...')
assert r.status_code == 200

# To YandexDictionary
text = 'green car'
key = 'dict......'
r = requests.post("https://dictionary.yandex.net/api/v1/dicservice.json/lookup",
                  data={'lang': 'en-ru', 'text': text, 'key': key})
print(r.status_code, r.reason)
print(r.text)

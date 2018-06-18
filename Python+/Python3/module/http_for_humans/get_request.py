# Send GET request
import requests

# To bugs.python.org
r = requests.get("https://www.google.ru/search", params={'q': 'football', 'bih': 980})
print(r.status_code, r.reason)
print(r.text[:200] + '...')
assert r.status_code == 200


# Retry HTTP request
import requests
from requests.adapters import HTTPAdapter

s = requests.Session()
url = 'http://not-exists.ru'
s.mount(url, HTTPAdapter(max_retries=10))

response = s.get(url)
assert response.status_code == 200

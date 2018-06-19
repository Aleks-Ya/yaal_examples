# Send GET request
import requests
from urllib3.exceptions import ReadTimeoutError

# To bugs.python.org

r = requests.get("https://www.google.ru/search", params={'q': 'football', 'bih': 980})
print(r.status_code, r.reason)
print(r.text[:200] + '...')
assert r.status_code == 200

# Timeout
timeout_secs = 0.1
try:
    r = requests.get("https://www.google.ru/search", params={'q': 'football', 'bih': 980}, timeout=timeout_secs)
    assert False
except (ReadTimeoutError, requests.exceptions.ConnectionError) as ex:
    print("Timeout is expected here!")


# Timeout 2
timeout_secs = 0.1
try:
    r = requests.get("https://www.google.ru/search", params={'q': 'football', 'bih': 980}, timeout=timeout_secs)
    assert False
except ReadTimeoutError as ex:
    print("Timeout is expected here: ", ex)
except requests.exceptions.ConnectionError as ex:
    print("Connection error is expected here: ", ex)

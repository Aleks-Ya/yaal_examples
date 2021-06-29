# Send GET HTTP request
import requests

response = requests.get('https://ya.ru/')
assert response.status_code == 200
assert len(response.content) > 0
print('Content:\n' + response.content.decode('utf-8'))

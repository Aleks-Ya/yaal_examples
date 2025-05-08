# Add params to a GET request that is sent via Session
import requests

url = 'https://webhook.site/0bee1ab8-a5c1-49dd-843d-19776efdb053'
params = {'name': 'John'}
sess = requests.session()
response = sess.get(url, params=params)

assert response.status_code == 200

# Cache a GET request
import datetime

import requests
from cachecontrol import CacheControl
from cachecontrol.caches import FileCache

sess = requests.session()
cached_sess = CacheControl(sess, cache=FileCache('.web_cache'))

for n in range(10):
    start_time = datetime.datetime.now()
    response = cached_sess.get("https://ya.ru")
    delta_time = datetime.datetime.now() - start_time
    print("Time delta: ", delta_time)
    assert response.status_code == 200

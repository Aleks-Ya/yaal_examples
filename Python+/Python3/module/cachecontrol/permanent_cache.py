# Permanent cache (forever)
import datetime

import requests
from cachecontrol import CacheControl
from cachecontrol.caches import FileCache
from cachecontrol.heuristics import ExpiresAfter

sess = requests.session()
forever_cache_storage = FileCache('.web_cache', forever=True)
heuristic = ExpiresAfter(weeks=52)
cached_sess = CacheControl(sess, cache=forever_cache_storage, heuristic=heuristic)

for n in range(10):
    start_time = datetime.datetime.now()
    response = cached_sess.get("https://ya.ru")
    delta_time = datetime.datetime.now() - start_time
    print("Time delta: ", delta_time)
    assert response.status_code == 200

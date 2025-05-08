# Permanent cache (forever)
import datetime
from datetime import datetime, timedelta

import requests
from cachecontrol import CacheControl
from cachecontrol.caches import FileCache
from cachecontrol.heuristics import ExpiresAfter
from requests import Session, Response


def test_permanent_cache():
    sess: Session = requests.session()
    forever_cache_storage: FileCache = FileCache('.web_cache', forever=True)
    heuristic: ExpiresAfter = ExpiresAfter(weeks=52)
    cached_sess: Session = CacheControl(sess, cache=forever_cache_storage, heuristic=heuristic)

    for n in range(10):
        start_time: datetime = datetime.now()
        response: Response = cached_sess.get("https://ya.ru")
        delta_time: timedelta = datetime.now() - start_time
        print("Time delta: ", delta_time)
        assert response.status_code == 200

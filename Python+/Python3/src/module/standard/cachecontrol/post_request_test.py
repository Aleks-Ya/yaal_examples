# Cache a POST request
from datetime import datetime, timedelta

import requests
from cachecontrol import CacheControl
from cachecontrol.caches import FileCache
from requests import Session, Response


def test_post_request():
    sess: Session = requests.session()
    cached_sess: Session = CacheControl(sess, cache=FileCache('.web_cache'))

    for n in range(10):
        start_time: datetime = datetime.now()
        response: Response = cached_sess.post("http://httpbin.org/post")
        delta_time: timedelta = datetime.now() - start_time
        print("Time delta: ", delta_time)
        assert response.status_code == 200

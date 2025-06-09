from pathlib import Path

from requests_cache import CachedSession, CachedResponse

from temp_helper import TempPath


def test_cache():
    cache_sqlite: Path = TempPath.temp_path_absent(".sqlite")
    print(cache_sqlite)
    session: CachedSession = CachedSession(cache_sqlite)
    for i in range(60):
        response: CachedResponse = session.get('https://httpbin.org/delay/1')
        assert response.status_code == 200

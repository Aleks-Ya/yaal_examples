from requests_cache import CachedSession, BaseCache, CachedResponse, FileCache


def test_memory_backend():
    backend: BaseCache = BaseCache()
    session: CachedSession = CachedSession(backend=backend)
    for i in range(60):
        response: CachedResponse = session.get('https://httpbin.org/delay/1')
        assert response.status_code == 200


def test_filesystem_backend():
    backend: FileCache = FileCache(use_temp=True)
    print(backend.cache_dir)
    session: CachedSession = CachedSession(backend=backend, serializer='json')
    for i in range(60):
        response: CachedResponse = session.get('https://httpbin.org/delay/1')
        assert response.status_code == 200

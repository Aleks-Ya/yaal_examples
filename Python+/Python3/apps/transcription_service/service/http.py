import requests


class Http:
    """Wrapper over 'requests' (for testing purposes)"""

    @staticmethod
    def post(url, data=None, json=None, **kwargs):
        r = requests.post(url, data, json, **kwargs)
        print(r.status_code, r.reason)
        print(r.text)
        return r

    @staticmethod
    def post_for_body(url, data=None, json=None, **kwargs):
        r = Http.post(url, data, json, **kwargs)
        return r.text

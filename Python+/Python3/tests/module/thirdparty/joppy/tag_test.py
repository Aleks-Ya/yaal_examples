from joppy.client_api import ClientApi
from joppy.data_types import TagData


def test_get_all_tags(api: ClientApi):
    tags: list[TagData] = api.get_all_tags()
    print(tags)

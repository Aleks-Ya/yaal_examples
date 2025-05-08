from pathlib import Path
from configparser import ConfigParser

from pyzotero.zotero import Zotero


def test_read_library():
    config: ConfigParser = ConfigParser()
    config.read(Path.home() / 'zotero_api/zotero_api.ini')

    library_id: str = config['ZOTERO']['library.id']
    api_key: str = config['ZOTERO']['api.key']

    zot: Zotero = Zotero(library_id, "user", api_key)
    items: list[dict] = zot.top(limit=5)
    for item in items:
        print('Item Type: %s | Key: %s' % (item['data']['itemType'], item['data']['key']))

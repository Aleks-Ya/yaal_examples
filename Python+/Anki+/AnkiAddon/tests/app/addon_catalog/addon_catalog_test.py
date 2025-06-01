from pathlib import Path

from app.addon_catalog.downloader import Downloader
from app.addon_catalog.json_storage import JsonStorage
from app.addon_catalog.parser import Parser
from app.addon_catalog.types import AddonHeader, AddonDetails


def test_parse_main_page():
    downloader: Downloader = Downloader()
    html: str = downloader.load_addons_page()
    addon_headers: list[AddonHeader] = Parser.parse_addons_page(html)
    print(addon_headers)

    details_list: list[AddonDetails] = []
    for addon_header in addon_headers:
        html: str = downloader.load_addon_page(addon_header.id)
        addon_details: AddonDetails = Parser.parse_addon_page(addon_header, html)
        details_list.append(addon_details)

    addons_json: str = JsonStorage.convert_addons_to_json(details_list)
    addons_json_file: Path = Path.home() / "tmp" / "anki_addons_page" / "addons.json"
    addons_json_file.write_text(addons_json)

    # TODO override given parsed values with manual
    # TODO get languages from GitHub
    # TODO output as JSON
    # TODO output as MarkDown

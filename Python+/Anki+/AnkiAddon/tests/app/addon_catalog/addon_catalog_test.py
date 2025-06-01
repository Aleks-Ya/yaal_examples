from pathlib import Path

from app.addon_catalog.parser import Parser
from app.addon_catalog.types import AddonHeader, AddonDetails


def test_parse_main_page():
    # html: str = Downloader.load_addons_page()
    html: str = (Path.home() / "tmp" / "anki_addons_page" / "Add-ons - AnkiWeb.html").read_text()
    addon_headers: list[AddonHeader] = Parser.parse_addons_page(html)
    print(addon_headers)

    addon_infos: list[AddonDetails] = []
    for row in addon_headers:
        # html: str = Downloader.load_addon_page(row.addon_page)
        html: str = (Path.home() / "tmp" / "anki_addons_page" / "Review Heatmap - AnkiWeb.html").read_text()
        addon_details: AddonDetails = Parser.parse_addon_page(row, html)
        addon_infos.append(addon_details)

    # TODO override given parsed values with manual
    # TODO output as JSON
    # TODO output as MarkDown

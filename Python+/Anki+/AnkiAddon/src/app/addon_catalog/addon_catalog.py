from pathlib import Path

from app.addon_catalog.data_types import AddonHeader, AddonDetails
from app.addon_catalog.downloader import Downloader
from app.addon_catalog.github_service_rest import GithubServiceRest
from app.addon_catalog.json_storage import JsonStorage
from app.addon_catalog.parser import Parser

if __name__ == "__main__":
    downloader: Downloader = Downloader()
    html: str = downloader.load_addons_page()
    addon_headers: list[AddonHeader] = Parser.parse_addons_page(html)
    print(addon_headers)

    github_service: GithubServiceRest = GithubServiceRest()
    parser: Parser = Parser(github_service)

    details_list: list[AddonDetails] = []
    for addon_header in addon_headers:
        html: str = downloader.load_addon_page(addon_header.id)
        addon_details: AddonDetails = parser.parse_addon_page(addon_header, html)
        details_list.append(addon_details)

    addons_json: str = JsonStorage.convert_addons_to_json(details_list)
    addons_json_file: Path = Path.home() / "tmp" / "anki_addons_page" / "addons.json"
    addons_json_file.write_text(addons_json)
    print(f"Output file: {addons_json_file}")

    # TODO override given parsed values with manual
    # TODO get languages from GitHub
    # TODO output as JSON
    # TODO output as MarkDown

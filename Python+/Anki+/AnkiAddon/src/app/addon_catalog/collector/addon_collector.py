from pathlib import Path

from app.addon_catalog.collector.addon_details_overwriter import AddonDetailsOverwriter
from app.addon_catalog.common.data_types import AddonHeader, AddonDetails
from app.addon_catalog.collector.downloader import Downloader
from app.addon_catalog.collector.github_service_rest import GithubServiceRest
from app.addon_catalog.collector.parser import Parser


class AddonCollector:

    @staticmethod
    def collect_addons() -> list[AddonDetails]:
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

        override_file: Path = Path(__file__).parent / "override.yaml"
        overridden_list: list[AddonDetails] = AddonDetailsOverwriter.overwrite(details_list, override_file)

        return overridden_list

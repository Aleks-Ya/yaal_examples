import time
from pathlib import Path

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.webdriver import WebDriver

from app.addon_catalog.collector.ankiweb.ankiweb_parser import AnkiWebParser
from app.addon_catalog.common.data_types import AddonId, AddonInfo, AddonHeader
from app.addon_catalog.common.json_helper import JsonHelper


class AnkiWebService:
    def __init__(self, dataset_dir: Path, cache_dir: Path, ankiweb_parser: AnkiWebParser) -> None:
        self.__ankiweb_parser: AnkiWebParser = ankiweb_parser
        options: Options = Options()
        options.add_argument('--headless')
        self.__driver: WebDriver = webdriver.Chrome(options=options)
        self.__cache_html_dir: Path = cache_dir / "anki-web" / "html"
        dataset_ankiweb_dir: Path = dataset_dir / "raw" / "1-anki-web"
        self.__dataset_html_dir: Path = dataset_ankiweb_dir / "html"
        self.__dataset_html_dir.mkdir(parents=True, exist_ok=True)
        self.__dataset_json_dir: Path = dataset_ankiweb_dir / "json" / "addon"

    def __del__(self) -> None:
        self.__driver.quit()

    def load_addon_infos(self) -> list[AddonInfo]:
        addon_headers: list[AddonHeader] = self.__get_headers()
        addon_infos: list[AddonInfo] = self.__get_addon_infos(addon_headers)
        print(f"Addon number: {len(addon_infos)}")
        return addon_infos

    def __get_headers(self) -> list[AddonHeader]:
        html: str = self.__load_addons_page()
        addons_html_file: Path = self.__dataset_html_dir / "addons.html"
        addons_html_file.write_text(html)
        addon_headers: list[AddonHeader] = self.__ankiweb_parser.parse_addons_page(html)
        return addon_headers

    def __get_addon_infos(self, addon_headers: list[AddonHeader]) -> list[AddonInfo]:
        dataset_addon_dir: Path = self.__dataset_html_dir / "addon"
        dataset_addon_dir.mkdir(parents=True, exist_ok=True)
        addon_infos: list[AddonInfo] = []
        for addon_header in addon_headers:
            html: str = self.__load_addon_page(addon_header.id)
            addon_html_file: Path = dataset_addon_dir / f"{addon_header.id}.html"
            addon_html_file.write_text(html)
            addon_info: AddonInfo = self.__ankiweb_parser.parse_addon_page(addon_header, html)
            addon_json_file: Path = self.__dataset_json_dir / f"{addon_header.id}.json"
            JsonHelper.write_addon_info_to_file(addon_info, addon_json_file)
            addon_infos.append(addon_info)
        return addon_infos

    def __load_addons_page(self) -> str:
        cache_file: Path = self.__cache_html_dir / "addons_page.html"
        if not cache_file.exists():
            print(f"Downloading addons page to {cache_file}")
            cache_file.parent.mkdir(parents=True, exist_ok=True)
            html: str = self.__load_page("https://ankiweb.net/shared/addons")
            cache_file.write_text(html)
        print(f"Reading addons page from {cache_file}")
        return cache_file.read_text()

    def __load_addon_page(self, addon_id: AddonId) -> str:
        cache_file: Path = self.__cache_html_dir / "addon" / f"{addon_id}.html"
        if not cache_file.exists():
            print(f"Downloading addon page to {cache_file}")
            cache_file.parent.mkdir(parents=True, exist_ok=True)
            html: str = self.__load_page(f"https://ankiweb.net/shared/info/{addon_id}")
            cache_file.write_text(html)
        return cache_file.read_text()

    def __load_page(self, url: str) -> str:
        self.__driver.get(url)
        time.sleep(3)
        return self.__driver.page_source

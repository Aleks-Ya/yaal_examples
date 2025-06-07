import time
from pathlib import Path

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.webdriver import WebDriver

from app.addon_catalog.collector.ankiweb.ankiweb_parser import AnkiWebParser
from app.addon_catalog.common.data_types import AddonId, AddonDetails, AddonHeader


class AnkiWebService:
    def __init__(self, cache_dir: Path) -> None:
        options: Options = Options()
        options.add_argument('--headless')
        self.__driver: WebDriver = webdriver.Chrome(options=options)
        self.__cache_dir: Path = cache_dir / "anki-web"

    def __del__(self) -> None:
        self.__driver.quit()

    def load_addons_list(self) -> list[AddonDetails]:
        html: str = self.__load_addons_page()
        addon_headers: list[AddonHeader] = AnkiWebParser.parse_addons_page(html)
        details_list: list[AddonDetails] = []
        for addon_header in addon_headers:
            html: str = self.__load_addon_page(addon_header.id)
            addon_details: AddonDetails = AnkiWebParser.parse_addon_page(addon_header, html)
            details_list.append(addon_details)
        return details_list

    def __load_addons_page(self) -> str:
        cache_file: Path = self.__cache_dir / "addons_page.html"
        if not cache_file.exists():
            print(f"Downloading addons page to {cache_file}")
            cache_file.parent.mkdir(parents=True, exist_ok=True)
            html: str = self.__load_page("https://ankiweb.net/shared/addons")
            cache_file.write_text(html)
        print(f"Loading addons page from {cache_file}")
        return cache_file.read_text()

    def __load_addon_page(self, addon_id: AddonId) -> str:
        cache_file: Path = self.__cache_dir / "addon" / f"{addon_id}.html"
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

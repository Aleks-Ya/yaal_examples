import tempfile
import time
from pathlib import Path

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.webdriver import WebDriver

from app.addon_catalog.data_types import AddonId


class Downloader:
    def __init__(self) -> None:
        options: Options = Options()
        options.add_argument('--headless')
        self.driver: WebDriver = webdriver.Chrome(options=options)
        self.cache_dir: Path = Path(tempfile.gettempdir()) / "addon_catalog" / "cache" / "anki-web"

    def __del__(self) -> None:
        self.driver.quit()

    def load_addons_page(self) -> str:
        cache_file: Path = self.cache_dir / "addons_page.html"
        if not cache_file.exists():
            print(f"Downloading addons page to {cache_file}")
            cache_file.parent.mkdir(parents=True, exist_ok=True)
            html: str = self.__load_page("https://ankiweb.net/shared/addons")
            cache_file.write_text(html)
        print(f"Loading addons page from {cache_file}")
        return cache_file.read_text()

    def load_addon_page(self, addon_id: AddonId) -> str:
        cache_file: Path = self.cache_dir / "addon" / f"{addon_id}.html"
        if not cache_file.exists():
            print(f"Downloading addon page to {cache_file}")
            cache_file.parent.mkdir(parents=True, exist_ok=True)
            html: str = self.__load_page(f"https://ankiweb.net/shared/info/{addon_id}")
            cache_file.write_text(html)
        return cache_file.read_text()

    def __load_page(self, url: str) -> str:
        self.driver.get(url)
        time.sleep(3)
        return self.driver.page_source

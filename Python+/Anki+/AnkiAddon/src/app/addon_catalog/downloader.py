import requests
from requests import Response


class Downloader:

    @staticmethod
    def load_addons_page() -> str:
        return Downloader.load_addon_page("https://ankiweb.net/shared/addons")

    @staticmethod
    def load_addon_page(url: str) -> str:
        response: Response = requests.get(url)
        if response.status_code != 200:
            raise Exception(f"Failed to fetch the page: {response}")
        return response.text

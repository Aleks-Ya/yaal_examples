import re

from bs4 import ResultSet, Tag, BeautifulSoup

from app.addon_catalog.types import AddonHeader, AddonDetails


class Parser:

    @staticmethod
    def parse_addons_page(html: str) -> list[AddonHeader]:
        soup: BeautifulSoup = BeautifulSoup(html, 'html.parser')
        addon_rows: list[AddonHeader] = []
        table_rows: ResultSet[Tag] = soup.find("main").find("table").find_all("tr")
        table_rows.pop(0)  # remove header
        for row in table_rows:
            cells: ResultSet[Tag] = row.find_all("td")
            title: str = cells[0].text
            addon_page: str = cells[0].find("a")["href"]
            addon_id: str = addon_page.split("/")[-1]
            rating: str = cells[1].text
            update_date: str = cells[2].text
            versions: str = cells[3].text
            addon_header: AddonHeader = AddonHeader(addon_id, title, addon_page, rating, update_date, versions)
            addon_rows.append(addon_header)
        return addon_rows

    @staticmethod
    def parse_addon_page(addon_header: AddonHeader, html: str) -> AddonDetails:
        all_links: list[str] = Parser.extract_all_links(html)
        github_links: list[str] = Parser.find_github_links(all_links)
        details: AddonDetails = AddonDetails(addon_header, github_links)
        return details

    @staticmethod
    def extract_all_links(html: str) -> list[str]:
        return re.findall(r'https?://\S+', html)

    @staticmethod
    def find_github_links(links: list[str]) -> list[str]:
        return [link for link in links if "github.com" in link]

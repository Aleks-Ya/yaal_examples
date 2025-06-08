from itertools import groupby
from typing import Optional

from bs4 import ResultSet, Tag, BeautifulSoup

from app.addon_catalog.collector.ankiweb.url_parser import UrlParser
from app.addon_catalog.common.data_types import AddonHeader, AddonDetails, AddonId, URL, GitHubLink, GitHubRepo


class AnkiWebParser:

    @staticmethod
    def parse_addons_page(html: str) -> list[AddonHeader]:
        soup: BeautifulSoup = BeautifulSoup(html, 'html.parser')
        addon_rows: list[AddonHeader] = []
        table_rows: ResultSet[Tag] = soup.find("main").find("table").find_all("tr")
        table_rows.pop(0)  # remove header
        for row in table_rows:
            cells: ResultSet[Tag] = row.find_all("td")
            title: str = cells[0].text
            addon_page: str = f"""https://ankiweb.net{cells[0].find("a")["href"]}"""
            addon_id: AddonId = AddonId(int(addon_page.split("/")[-1]))
            rating: str = cells[1].text
            update_date: str = cells[2].text
            versions: str = cells[3].text
            addon_header: AddonHeader = AddonHeader(addon_id, title, addon_page, rating, update_date, versions)
            addon_rows.append(addon_header)
        return addon_rows

    @staticmethod
    def parse_addon_page(addon_header: AddonHeader, html: str) -> AddonDetails:
        all_links: list[URL] = UrlParser.extract_all_links(html)
        github_links: list[GitHubLink] = UrlParser.find_github_links(all_links)
        other_links: list[URL] = [link for link in all_links if link not in github_links]
        github_repo: Optional[GitHubRepo] = AnkiWebParser.__deduct_github_repo_name(github_links)
        details: AddonDetails = AddonDetails(addon_header, github_links, other_links, github_repo, [], 0, None)
        return details

    @staticmethod
    def __deduct_github_repo_name(github_urls: list[GitHubLink]) -> Optional[GitHubRepo]:
        github_urls_sorted: list[GitHubLink] = [link for link in github_urls if link.repo is not None]
        github_urls_sorted.sort(key=lambda link: link.repo.get_id())
        grouped: groupby[GitHubRepo, GitHubLink] = groupby(github_urls_sorted, key=lambda link: link.repo)
        counts: dict[GitHubRepo, int] = {k: len(list(v)) for k, v in grouped}
        if len(counts) == 0:
            return None
        max_tuple: tuple[GitHubRepo, int] = max(counts.items(), key=lambda item: item[1])
        return max_tuple[0]

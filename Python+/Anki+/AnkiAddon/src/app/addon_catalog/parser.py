import re
from itertools import groupby
from re import Match
from typing import Optional

from bs4 import ResultSet, Tag, BeautifulSoup

from app.addon_catalog.types import AddonHeader, AddonDetails, AddonId, URL, GitHubLink, RepoName


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
            addon_page: str = f"""https://ankiweb.net{cells[0].find("a")["href"]}"""
            addon_id: AddonId = AddonId(addon_page.split("/")[-1])
            rating: str = cells[1].text
            update_date: str = cells[2].text
            versions: str = cells[3].text
            addon_header: AddonHeader = AddonHeader(addon_id, title, addon_page, rating, update_date, versions)
            addon_rows.append(addon_header)
        return addon_rows

    @staticmethod
    def parse_addon_page(addon_header: AddonHeader, html: str) -> AddonDetails:
        all_links: list[URL] = Parser.extract_all_links(html)
        github_links: list[GitHubLink] = Parser.find_github_links(all_links)
        other_links: list[URL] = [link for link in all_links if link not in github_links]
        github_repo: Optional[RepoName] = Parser.deduct_github_repo_name(github_links)
        details: AddonDetails = AddonDetails(addon_header, github_links, other_links, github_repo)
        return details

    @staticmethod
    def extract_all_links(html: str) -> list[URL]:
        return re.findall(r'https?://[^\s"<>\']+', html)

    @staticmethod
    def find_github_links(links: list[str]) -> list[GitHubLink]:
        return [Parser.parse_github_url(URL(link)) for link in links if "github.com" in link]

    @staticmethod
    def parse_github_url(url: URL) -> GitHubLink:
        pattern: str = r'github\.com[:/](?P<user>[^/]+)/(?P<repo>[^/]+)'
        match: Optional[Match[str]] = re.search(pattern, url)
        if match:
            return GitHubLink(url, match.group('user'), match.group('repo'))
        return GitHubLink(url, None, None)

    @staticmethod
    def deduct_github_repo_name(github_urls: list[GitHubLink]) -> Optional[RepoName]:
        github_urls_with_repo: list[GitHubLink] = [github_url for github_url in github_urls
                                                   if github_url.repo is not None]
        github_urls_with_repo.sort(key=lambda link: link.repo)
        grouped: groupby[RepoName, GitHubLink] = groupby(github_urls_with_repo, key=lambda link: link.repo)
        counts: dict[RepoName, int] = {k: len(list(v)) for k, v in grouped}
        if len(counts) == 0:
            return None
        max_tuple: tuple[RepoName, int] = max(counts.items(), key=lambda item: item[1])
        return max_tuple[0]

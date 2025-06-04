import re
from itertools import groupby
from re import Match
from typing import Optional

from bs4 import ResultSet, Tag, BeautifulSoup

from app.addon_catalog.common.data_types import AddonHeader, AddonDetails, AddonId, URL, GitHubLink, GitHubRepo, \
    RepoName, GithubUserName, GitHubUser, LanguageName
from app.addon_catalog.collector.github_service_rest import GithubServiceRest


class Parser:
    def __init__(self, github_service: GithubServiceRest):
        self.github_service: GithubServiceRest = github_service

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

    def parse_addon_page(self, addon_header: AddonHeader, html: str) -> AddonDetails:
        all_links: list[URL] = Parser.extract_all_links(html)
        github_links: list[GitHubLink] = Parser.find_github_links(all_links)
        other_links: list[URL] = [link for link in all_links if link not in github_links]
        github_repo: Optional[GitHubRepo] = Parser.deduct_github_repo_name(github_links)
        if github_repo:
            language_dict: dict[LanguageName, int] = self.github_service.get_languages(github_repo)
            languages: list[LanguageName] = list(language_dict.keys())
        else:
            languages: list[LanguageName] = []
        details: AddonDetails = AddonDetails(addon_header, github_links, other_links, github_repo, languages)
        return details

    @staticmethod
    def extract_all_links(html: str) -> list[URL]:
        return re.findall(r'https?://[^\s"<>\']+', html)

    @staticmethod
    def find_github_links(links: list[str]) -> list[GitHubLink]:
        parsed_links: list[Optional[GitHubLink]] = [Parser.parse_github_url(URL(link)) for link in links]
        return [link for link in parsed_links if link is not None]

    @staticmethod
    def parse_github_url(url: URL) -> Optional[GitHubLink]:
        repo_pattern: str = r'https://github\.com[:/](?P<user>[^/]+)/(?P<repo>[^/]+)'
        repo_match: Optional[Match[str]] = re.search(repo_pattern, url)
        if repo_match:
            user_name: GithubUserName = repo_match.group('user')
            user: GitHubUser = GitHubUser(user_name)
            repo_name: RepoName = repo_match.group('repo')
            repo: GitHubRepo = GitHubRepo(user_name, repo_name)
            return GitHubLink(url, user, repo)
        user_pattern: str = r'https://github\.com[:/](?P<user>[^/]+)'
        user_match: Optional[Match[str]] = re.search(user_pattern, url)
        if user_match:
            user_name: GithubUserName = user_match.group('user')
            user: GitHubUser = GitHubUser(user_name)
            return GitHubLink(url, user, None)
        return None

    @staticmethod
    def deduct_github_repo_name(github_urls: list[GitHubLink]) -> Optional[GitHubRepo]:
        github_urls_sorted: list[GitHubLink] = [link for link in github_urls if link.repo is not None]
        github_urls_sorted.sort(key=lambda link: link.repo.get_id())
        grouped: groupby[GitHubRepo, GitHubLink] = groupby(github_urls_sorted, key=lambda link: link.repo)
        counts: dict[GitHubRepo, int] = {k: len(list(v)) for k, v in grouped}
        if len(counts) == 0:
            return None
        max_tuple: tuple[GitHubRepo, int] = max(counts.items(), key=lambda item: item[1])
        return max_tuple[0]

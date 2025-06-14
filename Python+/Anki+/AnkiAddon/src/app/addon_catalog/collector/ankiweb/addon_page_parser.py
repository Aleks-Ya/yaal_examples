import datetime
import re
from datetime import date
from itertools import groupby
from re import Match
from typing import Optional

from bs4 import Tag, BeautifulSoup

from app.addon_catalog.collector.ankiweb.url_parser import UrlParser
from app.addon_catalog.collector.overrider.overrider import Overrider
from app.addon_catalog.common.data_types import AddonHeader, AddonInfo, AddonId, URL, GitHubLink, GitHubRepo, \
    GithubInfo, AddonPage, Version


class AddonPageParser:
    def __init__(self, overrider: Overrider) -> None:
        self.overrider: Overrider = overrider

    def parse_addon_page(self, addon_header: AddonHeader, html: str) -> AddonInfo:
        soup: BeautifulSoup = BeautifulSoup(html, 'html.parser')
        all_links: list[URL] = UrlParser.extract_all_links(html)
        github_links: list[GitHubLink] = UrlParser.find_github_links(all_links)
        other_links: list[URL] = [link for link in all_links if link not in github_links]
        github_repo: Optional[GitHubRepo] = self.__deduct_github_repo_name(addon_header.id, github_links)
        anki_forum_links: list[URL] = UrlParser.find_anki_forum_links(all_links)
        anki_forum_url: Optional[URL] = self.__deduct_anki_forum_url(addon_header.id, anki_forum_links)
        github_info: GithubInfo = GithubInfo(github_links, github_repo, [], 0, None, 0, 0)
        likes: int = self.__extract_likes(soup)
        dislikes: int = self.__extract_dislikes(soup)
        versions: list[Version] = self.__extract_versions(soup)
        addon_page: AddonPage = AddonPage(likes, dislikes, versions, other_links, anki_forum_url)
        addon_info: AddonInfo = AddonInfo(addon_header, addon_page, github_info)
        return addon_info

    def __deduct_github_repo_name(self, addon_id: AddonId, github_urls: list[GitHubLink]) -> Optional[GitHubRepo]:
        override_link: Optional[GitHubLink] = self.overrider.override_github_link(addon_id)
        if override_link:
            return override_link.repo
        not_null_urls: list[GitHubLink] = [link for link in github_urls if link.repo is not None]
        filtered_urls: list[GitHubLink] = AddonPageParser.__exclude_links(not_null_urls)
        filtered_urls.sort(key=lambda link: link.repo.get_id())
        grouped: groupby[GitHubRepo, GitHubLink] = groupby(filtered_urls, key=lambda link: link.repo)
        counts: dict[GitHubRepo, int] = {k: len(list(v)) for k, v in grouped}
        if len(counts) == 0:
            return None
        max_tuple: tuple[GitHubRepo, int] = max(counts.items(), key=lambda item: item[1])
        github_repo: GitHubRepo = max_tuple[0]
        return github_repo

    def __deduct_anki_forum_url(self, addon_id: AddonId, anki_forum_urls: list[URL]) -> Optional[URL]:
        override_url: Optional[URL] = self.overrider.override_anki_forum_url(addon_id)
        if override_url:
            return override_url
        urls_sorted: list[URL] = list(anki_forum_urls)
        urls_sorted.sort()
        grouped: groupby[URL, URL] = groupby(urls_sorted)
        counts: dict[URL, int] = {k: len(list(v)) for k, v in grouped}
        if len(counts) == 0:
            return None
        max_tuple: tuple[URL, int] = max(counts.items(), key=lambda item: item[1])
        return max_tuple[0]

    @staticmethod
    def __exclude_links(links: list[GitHubLink]) -> list[GitHubLink]:
        exclude_urls: list[str] = [
            'https://github.com/ankitects/anki',
            'https://github.com/ankidroid/anki-android'
        ]
        filtered_links: list[GitHubLink] = []
        for link in links:
            if all([not link.url.lower().startswith(prefix.lower()) for prefix in exclude_urls]):
                filtered_links.append(link)
        return filtered_links

    @staticmethod
    def __extract_likes(soup: BeautifulSoup) -> int:
        like_image: Tag = soup.find('img', alt='thumbs up')
        likes: int = int(like_image.next_sibling.get_text())
        return likes

    @staticmethod
    def __extract_dislikes(soup: BeautifulSoup) -> int:
        like_image: Tag = soup.find('img', alt='thumbs down')
        dislikes: int = int(like_image.next_sibling.get_text())
        return dislikes

    @staticmethod
    def __extract_versions(soup: BeautifulSoup) -> list[Version]:
        versions: list[Version] = []
        version_list: Tag = soup.find('ul', class_='mb-0')
        pattern: str = r'([\d.]+)-?([\d.+]+)? \(Updated (\d{4}-\d{2}-\d{2})\)'
        for version in version_list.find_all('li'):
            text: str = version.get_text().strip()
            match: Optional[Match[str]] = re.search(pattern, text)
            if match:
                group_count: int = len(match.groups())
                if group_count == 3:
                    min_version: str = match.group(1)
                    max_version: str = match.group(2)
                    update_date_str: str = match.group(3)
                elif group_count == 2:
                    min_version: str = match.group(1)
                    max_version: str = ""
                    update_date_str: str = match.group(2)
                else:
                    raise Exception(f'Cannot parse version: "{text}"')
                update_date: date = datetime.datetime.fromisoformat(update_date_str).date()
                version_info: Version = Version(min_version, max_version, update_date)
                versions.append(version_info)
            else:
                raise Exception(f'Cannot parse version: "{text}"')
        return versions

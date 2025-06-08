import re
from re import Match
from typing import Optional

from app.addon_catalog.common.data_types import URL, GitHubLink, GitHubRepo, RepoName, GithubUserName, GitHubUser


class UrlParser:

    @staticmethod
    def extract_all_links(html: str) -> list[URL]:
        return re.findall(r'https?://[^\s"<>\']+', html)

    @staticmethod
    def find_github_links(links: list[URL]) -> list[GitHubLink]:
        parsed_links: list[Optional[GitHubLink]] = [UrlParser.__parse_github_url(URL(link)) for link in links]
        return [link for link in parsed_links if link is not None]

    @staticmethod
    def find_anki_forum_links(links: list[URL]) -> list[URL]:
        parsed_links: list[Optional[URL]] = [UrlParser.__parse_anki_forum_url(URL(link)) for link in links]
        return [link for link in parsed_links if link is not None]

    @staticmethod
    def __parse_github_url(url: URL) -> Optional[GitHubLink]:
        repo_pattern: str = r'https://github\.com[:/](?P<user>[^/]+)/(?P<repo>[^/?#)(]+)'
        repo_match: Optional[Match[str]] = re.search(repo_pattern, url)
        if repo_match:
            user_name: GithubUserName = GithubUserName(repo_match.group('user').lower())
            user: GitHubUser = GitHubUser(user_name)
            repo_name: RepoName = RepoName(repo_match.group('repo').lower())
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
    def __parse_anki_forum_url(url: URL) -> Optional[URL]:
        pattern: str = r"https://forums\.ankiweb\.net/t/[\w\-]+/\d+"
        match: Optional[Match[str]] = re.match(pattern, url)
        return url if match else None

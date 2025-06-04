from typing import NewType, Optional
from dataclasses import dataclass

AddonId = NewType("AddonId", int)
URL = NewType("URL", str)
RepoName = NewType("RepoName", str)
GithubUserName = NewType("GithubUserName", str)
RepoId = NewType("RepoId", str)
LanguageName = NewType("LanguageName", str)


@dataclass
class AddonHeader:
    id: AddonId
    title: str
    addon_page: str
    rating: str
    update_date: str
    versions: str


@dataclass
class GitHubUser:
    user_name: GithubUserName

    def get_url(self) -> URL:
        return URL(f"https://github.com/{self.user_name}")


@dataclass(frozen=True)
class GitHubRepo:
    user: GithubUserName
    repo_name: RepoName

    def get_id(self) -> RepoId:
        return RepoId(f"{self.user}/{self.repo_name}")

    def get_url(self) -> URL:
        return URL(f"https://github.com/{self.user}/{self.repo_name}")


@dataclass
class GitHubLink:
    url: URL
    user: GitHubUser
    repo: Optional[GitHubRepo]


@dataclass
class AddonDetails:
    header: AddonHeader
    github_links: list[GitHubLink]
    other_links: list[URL]
    github_repo_id: Optional[RepoId]
    languages: list[LanguageName]

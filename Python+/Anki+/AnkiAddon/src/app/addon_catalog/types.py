import dataclasses
from typing import NewType, Optional

AddonId = NewType("AddonId", str)
URL = NewType("URL", str)
RepoName = NewType("RepoName", str)


@dataclasses.dataclass
class AddonHeader:
    id: AddonId
    title: str
    addon_page: str
    rating: str
    update_date: str
    versions: str


@dataclasses.dataclass
class GitHubLink:
    url: URL
    user: Optional[str]
    repo: Optional[RepoName]


@dataclasses.dataclass
class AddonDetails:
    header: AddonHeader
    github_links: list[GitHubLink]
    other_links: list[URL]
    github_repo: Optional[RepoName]

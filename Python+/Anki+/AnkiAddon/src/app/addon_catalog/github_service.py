import json
import tempfile
from pathlib import Path
from time import sleep
from typing import Any

from github import Github
from github.Repository import Repository

from app.addon_catalog.data_types import GitHubRepo, LanguageName


class GithubService:
    def __init__(self):
        self.github: Github = Github()
        self.cache_dir: Path = Path(tempfile.gettempdir()) / "addon_catalog" / "cache" / "github"

    def __del__(self):
        self.github.close()

    def get_languages(self, repo: GitHubRepo) -> dict[LanguageName, int]:
        cache_file: Path = self.cache_dir / repo.user / f"{repo.repo_name}.json"
        languages_property: str = "languages"
        if not cache_file.exists():
            print(f"Downloading languages for {repo.get_id()} to {cache_file}")
            cache_file.parent.mkdir(parents=True, exist_ok=True)
            try:
                repo: Repository = self.github.get_repo(repo.get_id())
                languages: dict[str, int] = repo.get_languages()
                sleep(3)
            except Exception as e:
                print(f"Failed to get languages for {repo.get_id()}: {e}")
                languages: dict[LanguageName, int] = {}
            content: dict[str, Any] = {languages_property: languages}
            content_json: str = json.dumps(content, indent=2)
            cache_file.write_text(content_json)
        content_json: str = cache_file.read_text()
        content: dict[str, Any] = json.loads(content_json)
        languages: dict[LanguageName, int] = content[languages_property]
        return languages

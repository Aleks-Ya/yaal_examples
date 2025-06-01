import json
import tempfile
from pathlib import Path

from github import Github
from github.Repository import Repository


class GithubService:
    def __init__(self):
        self.github: Github = Github()
        self.cache_dir: Path = Path(tempfile.gettempdir()) / "addon_catalog" / "cache" / "github"

    def __del__(self):
        self.github.close()

    def get_languages(self, repo_name: str) -> dict[str, int]:
        cache_file: Path = self.cache_dir / f"{repo_name}.json"
        if not cache_file.exists():
            print(f"Downloading languages for {repo_name} to {cache_file}")
            cache_file.parent.mkdir(parents=True, exist_ok=True)
            repo: Repository = self.github.get_repo(repo_name)
            languages: dict[str, int] = repo.get_languages()
            languages_json: str = json.dumps(languages, indent=2)
            cache_file.write_text(languages_json)
        languages_json: str = cache_file.read_text()
        languages: dict[str, int] = json.loads(languages_json)
        return languages

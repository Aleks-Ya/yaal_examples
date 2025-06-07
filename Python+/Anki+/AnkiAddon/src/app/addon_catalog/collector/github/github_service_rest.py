from pathlib import Path
from time import sleep
from typing import Any
import json

from requests import Response
import requests

from app.addon_catalog.common.data_types import GitHubRepo, LanguageName


class GithubServiceRest:
    def __init__(self, cache_dir: Path):
        token_file: Path = Path.home() / ".github" / "token.txt"
        token: str = token_file.read_text().strip()
        self.__headers: dict[str, str] = {
            'Authorization': f'Bearer {token}'
        }
        self.__cache_dir: Path = cache_dir / "github"
        self.__languages_property: str = "languages"

    def get_languages(self, repo: GitHubRepo) -> dict[LanguageName, int]:
        cache_file: Path = self.__cache_dir / repo.user / f"{repo.repo_name}.json"
        if not cache_file.exists():
            print(f"Downloading languages for {repo.get_id()} to {cache_file}")
            cache_file.parent.mkdir(parents=True, exist_ok=True)
            url: str = f"https://api.github.com/repos/{repo.user}/{repo.repo_name}/languages"
            sleep(3)
            response: Response = requests.request("GET", url, headers=self.__headers)
            if response.status_code == 200:
                obj_list: dict[str, int] = json.loads(response.text)
                languages: dict[LanguageName, int] = {LanguageName(k): v for k, v in obj_list.items()}
                self.__write_languages(cache_file, languages)
            elif response.status_code == 404:
                print(f"Repo {repo.get_id()} not found: {url}")
                self.__write_languages(cache_file, {})
            else:
                raise Exception(f"Error status {response.status_code} for {repo.get_id()}: {response.text}")
        content_json: str = cache_file.read_text()
        content: dict[str, Any] = json.loads(content_json)
        languages: dict[LanguageName, int] = content[self.__languages_property]
        return languages

    def __write_languages(self, cache_file: Path, languages: dict[LanguageName, int]) -> None:
        content: dict[str, Any] = {self.__languages_property: languages}
        content_json: str = json.dumps(content, indent=2)
        cache_file.write_text(content_json)

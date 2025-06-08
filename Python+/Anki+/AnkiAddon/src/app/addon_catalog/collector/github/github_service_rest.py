from pathlib import Path
from time import sleep
import json
from typing import Any

from requests import Response
import requests

from app.addon_catalog.common.data_types import GitHubRepo, LanguageName
from app.addon_catalog.common.json_helper import JsonHelper


class GithubServiceRest:
    def __init__(self, dataset_dir: Path, cache_dir: Path):
        token_file: Path = Path.home() / ".github" / "token.txt"
        token: str = token_file.read_text().strip()
        self.__headers: dict[str, str] = {
            'Authorization': f'Bearer {token}'
        }
        self.__dataset_dir: Path = dataset_dir / "github"
        self.__cache_dir: Path = cache_dir / "github"
        self.__languages_property: str = "languages"

    def get_languages(self, repo: GitHubRepo) -> dict[LanguageName, int]:
        cache_file: Path = self.__cache_dir / repo.user / repo.repo_name / "languages.json"
        if not cache_file.exists():
            print(f"Downloading languages for {repo.get_id()} to {cache_file}")
            url: str = f"https://api.github.com/repos/{repo.user}/{repo.repo_name}/languages"
            sleep(2)
            response: Response = requests.request("GET", url, headers=self.__headers)
            if response.status_code == 200:
                JsonHelper.write_content_to_file(response.text, cache_file)
            elif response.status_code == 404:
                print(f"Repo {repo.get_id()} not found: {url}")
                JsonHelper.write_dict_to_file({}, cache_file)
                not_found_file: Path = self.__cache_dir / repo.user / repo.repo_name / "NOT_FOUND_404"
                not_found_file.write_text("")
            else:
                raise Exception(f"Error status {response.status_code} for {repo.get_id()}: {response.text}")
        content_json: str = cache_file.read_text()
        languages: dict[LanguageName, int] = json.loads(content_json)
        dataset_file: Path = self.__dataset_dir / repo.user / repo.repo_name / "languages.json"
        JsonHelper.write_dict_to_file(languages, dataset_file)
        return languages

    def get_stars_count(self, repo: GitHubRepo) -> int:
        if not repo:
            return 0
        cache_file: Path = self.__cache_dir / repo.user / repo.repo_name / "info.json"
        if not cache_file.exists():
            if self.__is_repo_marked_as_not_found(repo):
                print(f"Give zero stars for a not found repo: {repo.get_id()}")
                JsonHelper.write_dict_to_file({}, cache_file)
                return 0
            print(f"Downloading info for {repo.get_id()} to {cache_file}")
            url: str = f"https://api.github.com/repos/{repo.user}/{repo.repo_name}"
            sleep(2)
            response: Response = requests.request("GET", url, headers=self.__headers)
            if response.status_code == 200:
                JsonHelper.write_content_to_file(response.text, cache_file)
            elif response.status_code == 404:
                print(f"Repo {repo.get_id()} not found: {url}")
                JsonHelper.write_dict_to_file({}, cache_file)
                not_found_file: Path = self.__cache_dir / repo.user / repo.repo_name / "NOT_FOUND_404"
                not_found_file.write_text("")
            else:
                raise Exception(f"Error status {response.status_code} for {repo.get_id()}: {response.text}")
        content_json: str = cache_file.read_text()
        json_dict: dict[str, Any] = json.loads(content_json)
        stars_count: int = json_dict["stargazers_count"] if "stargazers_count" in json_dict else 0
        dataset_file: Path = self.__dataset_dir / repo.user / repo.repo_name / "info.json"
        JsonHelper.write_dict_to_file(json_dict, dataset_file)
        return stars_count

    def __is_repo_marked_as_not_found(self, repo: GitHubRepo) -> bool:
        not_found_file: Path = self.__cache_dir / repo.user / repo.repo_name / "NOT_FOUND_404"
        return not_found_file.exists()

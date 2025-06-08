from datetime import datetime
from pathlib import Path
from time import sleep
import json
from typing import Any, Optional

from requests import Response
import requests

from app.addon_catalog.common.data_types import GitHubRepo, LanguageName
from app.addon_catalog.common.json_helper import JsonHelper


class GithubService:
    __sleep_sec: int = 1

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
            sleep(GithubService.__sleep_sec)
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
        dataset_file: Path = self.__dataset_dir / repo.user / repo.repo_name / f"{repo.user}_{repo.repo_name}_languages.json"
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
            sleep(GithubService.__sleep_sec)
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
        dataset_file: Path = self.__dataset_dir / repo.user / repo.repo_name / f"{repo.user}_{repo.repo_name}_info.json"
        JsonHelper.write_dict_to_file(json_dict, dataset_file)
        return stars_count

    def get_last_commit(self, repo: GitHubRepo) -> Optional[datetime]:
        if not repo:
            return None
        cache_file: Path = self.__cache_dir / repo.user / repo.repo_name / "last-commit.json"
        if not cache_file.exists():
            if self.__is_repo_marked_as_not_found(repo):
                print(f"Skip getting the last commit for a not found repo: {repo.get_id()}")
                JsonHelper.write_dict_to_file({}, cache_file)
                return None
            print(f"Downloading last commit for {repo.get_id()} to {cache_file}")
            url: str = f"https://api.github.com/repos/{repo.user}/{repo.repo_name}/commits?per_page=1"
            sleep(GithubService.__sleep_sec)
            response: Response = requests.request("GET", url, headers=self.__headers)
            if response.status_code == 200:
                JsonHelper.write_content_to_file(response.text, cache_file)
            elif response.status_code == 404:
                print(f"Repo not found: {url}")
                JsonHelper.write_dict_to_file({}, cache_file)
                not_found_file: Path = self.__cache_dir / repo.user / repo.repo_name / "NOT_FOUND_404"
                not_found_file.write_text("")
            elif response.status_code == 409:
                print(f"Repo is empty: {url}")
                JsonHelper.write_dict_to_file({}, cache_file)
            else:
                raise Exception(f"Error status {response.status_code} for {repo.get_id()}: {response.text}")
        content_json: str = cache_file.read_text()
        commit_list: list[dict[str, Any]] = json.loads(content_json)
        if len(commit_list) == 0:
            return None
        json_dict: dict[str, Any] = commit_list[0]
        date: Optional[str] = json_dict.get("commit", {}).get("committer", {}).get("date")
        date_obj: Optional[datetime] = datetime.strptime(date, "%Y-%m-%dT%H:%M:%SZ") if date else None
        dataset_file: Path = self.__dataset_dir / repo.user / repo.repo_name / f"{repo.user}_{repo.repo_name}_last-commit.json"
        JsonHelper.write_dict_to_file(json_dict, dataset_file)
        return date_obj

    def __is_repo_marked_as_not_found(self, repo: GitHubRepo) -> bool:
        not_found_file: Path = self.__cache_dir / repo.user / repo.repo_name / "NOT_FOUND_404"
        return not_found_file.exists()

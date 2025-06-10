from datetime import datetime
from pathlib import Path
from time import sleep
from typing import Any, Optional

from requests import Response
import requests

from app.addon_catalog.collector.github.handler.actions_repo_handler import ActionsRepoHandler
from app.addon_catalog.collector.github.handler.languages_repo_handler import LanguagesRepoHandler
from app.addon_catalog.collector.github.handler.last_commit_repo_handler import LastCommitRepoHandler
from app.addon_catalog.collector.github.handler.repo_handler import RepoHandler
from app.addon_catalog.collector.github.handler.stars_repo_handler import StarsRepoHandler
from app.addon_catalog.collector.github.handler.tests_repo_handler import TestsRepoHandler
from app.addon_catalog.common.data_types import GitHubRepo, LanguageName


class GithubService:
    __sleep_sec: int = 1

    def __init__(self, dataset_dir: Path, cache_dir: Path):
        token_file: Path = Path.home() / ".github" / "token.txt"
        token: str = token_file.read_text().strip()
        self.__headers: dict[str, str] = {
            'Authorization': f'Bearer {token}'
        }
        self.__dataset_dir: Path = dataset_dir / "raw" / "2-github"
        self.__cache_dir: Path = cache_dir / "github"

    def get_languages(self, repo: GitHubRepo) -> dict[LanguageName, int]:
        handler: RepoHandler = LanguagesRepoHandler(repo, self.__cache_dir, self.__dataset_dir)
        return self.__get_value(handler, repo)

    def get_stars_count(self, repo: GitHubRepo) -> int:
        handler: RepoHandler = StarsRepoHandler(repo, self.__cache_dir, self.__dataset_dir)
        return self.__get_value(handler, repo)

    def get_last_commit(self, repo: GitHubRepo) -> Optional[datetime]:
        handler: RepoHandler = LastCommitRepoHandler(repo, self.__cache_dir, self.__dataset_dir)
        return self.__get_value(handler, repo)

    def get_action_count(self, repo: GitHubRepo) -> Optional[int]:
        handler: RepoHandler = ActionsRepoHandler(repo, self.__cache_dir, self.__dataset_dir)
        return self.__get_value(handler, repo)

    def get_tests_count(self, repo: GitHubRepo) -> Optional[int]:
        handler: RepoHandler = TestsRepoHandler(repo, self.__cache_dir, self.__dataset_dir)
        return self.__get_value(handler, repo)

    def __get_value(self, handler: RepoHandler, repo: GitHubRepo) -> Optional[Any]:
        if not repo:
            return None
        if not handler.is_cached():
            if handler.is_repo_marked_as_not_found():
                return handler.get_not_found_return_value()
            print(f"Downloading {handler.get_cache_filename()} for {repo.get_id()}")
            url: str = handler.get_url()
            sleep(GithubService.__sleep_sec)
            response: Response = requests.request("GET", url, headers=self.__headers)
            if response.status_code == 200:
                handler.status_200(response)
            elif response.status_code == 404:
                handler.status_404()
            elif response.status_code == 409:
                handler.status_409(response)
            else:
                handler.status_other(response)
        return_value: Optional[Any] = handler.extract_return_value()
        handler.write_dataset(return_value)
        return return_value

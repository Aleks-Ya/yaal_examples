import json
from abc import abstractmethod, ABC
from pathlib import Path
from typing import Any, Optional

from requests import Response

from app.addon_catalog.common.data_types import GitHubRepo
from app.addon_catalog.common.json_helper import JsonHelper


class RepoHandler(ABC):
    def __init__(self, repo: GitHubRepo, cache_dir: Path, dataset_dir: Path) -> None:
        self._repo: GitHubRepo = repo
        self.__cache_dir: Path = cache_dir
        self.__dataset_dir: Path = dataset_dir
        self.__not_found_file: Path = self.__cache_dir / self._repo.user / self._repo.repo_name / "NOT_FOUND_404"

    def is_cached(self) -> bool:
        return self.get_cache_file().exists()

    def get_not_found_return_value(self) -> Any:
        return None

    def status_200(self, response: Response) -> None:
        cache_file: Path = self.get_cache_file()
        JsonHelper.write_content_to_file(response.text, cache_file)

    def status_404(self) -> None:
        cache_file: Path = self.get_cache_file()
        url: str = self.get_url()
        print(f"Repo not found: {url}")
        JsonHelper.write_dict_to_file({}, cache_file)
        self.__not_found_file.write_text("")

    def status_409(self, response: Response) -> None:
        self.status_other(response)

    def status_other(self, response: Response) -> None:
        raise Exception(f"Error status {response.status_code} for {self._repo.get_id()}: {response.text}")

    @abstractmethod
    def get_url(self) -> str:
        ...

    @abstractmethod
    def get_cache_filename(self) -> str:
        ...

    @abstractmethod
    def get_dataset_filename(self) -> str:
        ...

    def get_cache_file(self) -> Path:
        name: str = self.get_cache_filename()
        return self.__cache_dir / self._repo.user / self._repo.repo_name / f"{name}.json"

    def get_dataset_file(self) -> Path:
        name: str = self.get_dataset_filename()
        return self.__dataset_dir / self._repo.user / self._repo.repo_name / f"{self._repo.user}_{self._repo.repo_name}_{name}.json"

    def extract_return_value(self) -> Optional[Any]:
        cache_file: Path = self.get_cache_file()
        content_dict: object = json.loads(cache_file.read_text())
        return self.extract_return_value_from_dict(content_dict)

    @abstractmethod
    def extract_return_value_from_dict(self, content_obj: object) -> Any:
        ...

    @abstractmethod
    def prepare_dataset_dict(self, return_value: Any) -> dict[str, Any]:
        ...

    def write_dataset(self, return_value: Any) -> None:
        dataset_dict: dict[str, Any] = self.prepare_dataset_dict(return_value)
        dataset_file: Path = self.get_dataset_file()
        JsonHelper.write_dict_to_file(dataset_dict, dataset_file)

    def is_repo_marked_as_not_found(self) -> bool:
        return self.__not_found_file.exists()

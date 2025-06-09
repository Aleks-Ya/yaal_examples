from typing import Any

from app.addon_catalog.collector.github.handler.repo_handler import RepoHandler
from app.addon_catalog.common.data_types import LanguageName


class LanguagesRepoHandler(RepoHandler):

    def get_cache_filename(self) -> str:
        return "languages"

    def get_dataset_filename(self) -> str:
        return "languages"

    def get_url(self) -> str:
        return f"https://api.github.com/repos/{self._repo.user}/{self._repo.repo_name}/languages"

    def extract_return_value_from_dict(self, content_obj: dict[str, Any]) -> dict[LanguageName, int]:
        return {LanguageName(k): v for k, v in content_obj.items()}

    def prepare_dataset_dict(self, return_value: dict[LanguageName, int]) -> dict[str, Any]:
        return return_value

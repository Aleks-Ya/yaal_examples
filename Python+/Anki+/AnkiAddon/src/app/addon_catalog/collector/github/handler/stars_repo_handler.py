from typing import Any

from app.addon_catalog.collector.github.handler.repo_handler import RepoHandler


class StarsRepoHandler(RepoHandler):

    def get_cache_filename(self) -> str:
        return "info"

    def get_dataset_filename(self) -> str:
        return "stars-count"

    def get_url(self) -> str:
        return f"https://api.github.com/repos/{self._repo.user}/{self._repo.repo_name}"

    def extract_return_value_from_dict(self, content_obj: dict[str, Any]) -> int:
        return content_obj["stargazers_count"] if "stargazers_count" in content_obj else 0

    def prepare_dataset_dict(self, return_value: Any) -> dict[str, Any]:
        return {"stars_count": return_value}

    def get_not_found_return_value(self) -> int:
        return 0

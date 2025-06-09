from typing import Any

from app.addon_catalog.collector.github.handler.repo_handler import RepoHandler


class ActionsRepoHandler(RepoHandler):

    def get_cache_filename(self) -> str:
        return "actions"

    def get_dataset_filename(self) -> str:
        return "action-count"

    def get_url(self) -> str:
        return f"https://api.github.com/repos/{self._repo.user}/{self._repo.repo_name}/actions/workflows"

    def extract_return_value_from_dict(self, content_obj: dict[str, Any]) -> int:
        return content_obj.get("total_count", 0)

    def prepare_dataset_dict(self, return_value: Any) -> dict[str, Any]:
        return {"total_count": return_value}

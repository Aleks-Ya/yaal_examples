from pathlib import Path
from typing import Any

from requests import Response

from app.addon_catalog.collector.github.handler.repo_handler import RepoHandler
from app.addon_catalog.collector.github.handler.tests_counter import TestsCounter
from app.addon_catalog.common.json_helper import JsonHelper


class TestsRepoHandler(RepoHandler):

    def get_cache_filename(self) -> str:
        return "tree"

    def get_dataset_filename(self) -> str:
        return "tests-count"

    def get_url(self) -> str:
        return f"https://api.github.com/repos/{self._repo.user}/{self._repo.repo_name}/git/trees/HEAD?recursive=1"

    def extract_return_value_from_dict(self, content_obj: dict[str, Any]) -> int:
        is_truncated: bool = content_obj.get("truncated")
        if is_truncated:
            raise Exception(f"Repo tree is truncated: {content_obj['url']}")
        if "tree" not in content_obj:
            return 0
        files: list[str] = [file["path"] for file in content_obj["tree"]]
        return TestsCounter.count_tests(files)

    def prepare_dataset_dict(self, return_value: int) -> dict[str, Any]:
        return {"tests_count": return_value}

    def status_409(self, response: Response) -> None:
        print(f"Repo is empty: {self.get_url()}")
        cache_file: Path = self.get_cache_file()
        JsonHelper.write_dict_to_file({}, cache_file)

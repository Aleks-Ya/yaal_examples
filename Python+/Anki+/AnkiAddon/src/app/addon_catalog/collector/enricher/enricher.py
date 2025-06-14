from datetime import datetime
from pathlib import Path
from typing import Optional

from app.addon_catalog.common.data_types import AddonInfo, LanguageName, GithubInfo
from app.addon_catalog.collector.github.github_service import GithubService
from app.addon_catalog.common.json_helper import JsonHelper


class Enricher:
    def __init__(self, dataset_dir: Path, github_service: GithubService):
        self.__dataset_dir: Path = dataset_dir / "raw" / "3-enricher" / "addon"
        self.__dataset_dir.mkdir(parents=True, exist_ok=True)
        self.__github_service: GithubService = github_service

    def enrich_list(self, addon_infos: list[AddonInfo]) -> list[AddonInfo]:
        return [self.enrich(addon_info) for addon_info in addon_infos]

    def enrich(self, addon_info: AddonInfo) -> AddonInfo:
        languages: list[LanguageName] = self.__get_languages(addon_info)
        stars: int = self.__github_service.get_stars_count(addon_info.github.github_repo)
        last_commit: Optional[datetime] = self.__github_service.get_last_commit(addon_info.github.github_repo)
        action_count: int = self.__github_service.get_action_count(addon_info.github.github_repo)
        tests_count: int = self.__github_service.get_tests_count(addon_info.github.github_repo)
        github_info: GithubInfo = GithubInfo(addon_info.github.github_links, addon_info.github.github_repo,
                                             languages, stars, last_commit, action_count, tests_count)
        enriched_addon_info: AddonInfo = AddonInfo(addon_info.header, addon_info.page, github_info)
        addon_json_file: Path = self.__dataset_dir / f"{addon_info.header.id}.json"
        JsonHelper.write_addon_info_to_file(addon_info, addon_json_file)
        return enriched_addon_info

    def __get_languages(self, addon_info: AddonInfo) -> list[LanguageName]:
        if addon_info.github.github_repo:
            language_dict: dict[LanguageName, int] = self.__github_service.get_languages(
                addon_info.github.github_repo)
            languages: list[LanguageName] = list(language_dict.keys())
        else:
            languages: list[LanguageName] = []
        return languages

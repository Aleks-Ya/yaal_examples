from pathlib import Path

from app.addon_catalog.common.data_types import AddonDetails, LanguageName
from app.addon_catalog.collector.github.github_service_rest import GithubServiceRest
from app.addon_catalog.common.json_helper import JsonHelper


class Enricher:
    def __init__(self, dataset_dir: Path, github_service: GithubServiceRest):
        self.__dataset_dir: Path = dataset_dir / "enricher" / "addon"
        self.__dataset_dir.mkdir(parents=True, exist_ok=True)
        self.__github_service: GithubServiceRest = github_service

    def enrich_list(self, addon_details_list: list[AddonDetails]) -> list[AddonDetails]:
        return [self.enrich(addon_details) for addon_details in addon_details_list]

    def enrich(self, addon_details: AddonDetails) -> AddonDetails:
        languages: list[LanguageName] = self.__get_languages(addon_details)
        stars: int = self.__github_service.get_stars_count(addon_details.github_repo)
        enriched_details: AddonDetails = AddonDetails(addon_details.header, addon_details.github_links,
                                                      addon_details.other_links, addon_details.github_repo,
                                                      languages, stars)
        addon_json_file: Path = self.__dataset_dir / f"{addon_details.header.id}.json"
        JsonHelper.write_addon_details_to_file(addon_details, addon_json_file)
        return enriched_details

    def __get_languages(self, addon_details) -> list[LanguageName]:
        if addon_details.github_repo:
            language_dict: dict[LanguageName, int] = self.__github_service.get_languages(addon_details.github_repo)
            languages: list[LanguageName] = list(language_dict.keys())
        else:
            languages: list[LanguageName] = []
        return languages

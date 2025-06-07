from app.addon_catalog.common.data_types import AddonDetails, LanguageName
from app.addon_catalog.collector.github.github_service_rest import GithubServiceRest


class Enricher:
    def __init__(self, github_service: GithubServiceRest):
        self.github_service: GithubServiceRest = github_service

    def enrich_list(self, addon_details_list: list[AddonDetails]) -> list[AddonDetails]:
        return [self.enrich(addon_details) for addon_details in addon_details_list]

    def enrich(self, addon_details: AddonDetails) -> AddonDetails:
        if addon_details.github_repo:
            language_dict: dict[LanguageName, int] = self.github_service.get_languages(addon_details.github_repo)
            languages: list[LanguageName] = list(language_dict.keys())
        else:
            languages: list[LanguageName] = []
        return AddonDetails(addon_details.header, addon_details.github_links, addon_details.other_links,
                            addon_details.github_repo, languages)

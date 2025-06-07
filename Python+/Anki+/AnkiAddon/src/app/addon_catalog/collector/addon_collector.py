from app.addon_catalog.collector.enricher.enricher import Enricher
from app.addon_catalog.collector.overrider.addon_details_overrider import AddonDetailsOverrider
from app.addon_catalog.common.data_types import AddonDetails
from app.addon_catalog.collector.ankiweb.ankiweb_service import AnkiWebService
from app.addon_catalog.collector.github.github_service_rest import GithubServiceRest


class AddonCollector:
    def __init__(self, ankiweb_service: AnkiWebService, github_service: GithubServiceRest, enricher: Enricher):
        self.__ankiweb_service: AnkiWebService = ankiweb_service
        self.__github_service: GithubServiceRest = github_service
        self.__enricher: Enricher = enricher

    def collect_addons(self) -> list[AddonDetails]:
        details_list: list[AddonDetails] = self.__ankiweb_service.load_addons_list()
        enriched_details_list: list[AddonDetails] = self.__enricher.enrich_list(details_list)
        overridden_list: list[AddonDetails] = AddonDetailsOverrider.overwrite(enriched_details_list)
        return overridden_list

import shutil
from pathlib import Path

from app.addon_catalog.aggregator.aggregator import Aggregator
from app.addon_catalog.collector.addon_collector import AddonCollector
from app.addon_catalog.collector.ankiweb.addon_page_parser import AddonPageParser
from app.addon_catalog.collector.ankiweb.ankiweb_service import AnkiWebService
from app.addon_catalog.collector.enricher.enricher import Enricher
from app.addon_catalog.collector.github.github_service import GithubService
from app.addon_catalog.collector.overrider.overrider import Overrider
from app.addon_catalog.common.data_types import AddonInfo, Aggregation
from app.addon_catalog.exporter.exporter_facade import ExporterFacade

if __name__ == "__main__":
    working_dir: Path = Path.home() / "anki-addon-catalog"
    dataset_dir: Path = working_dir / "dataset"
    cache_dir: Path = working_dir / "cache"
    shutil.rmtree(dataset_dir, ignore_errors=True)
    overrider: Overrider = Overrider(dataset_dir)
    addon_page_parser: AddonPageParser = AddonPageParser(overrider)
    ankiweb_service: AnkiWebService = AnkiWebService(dataset_dir, cache_dir, addon_page_parser)
    github_service: GithubService = GithubService(dataset_dir, cache_dir)
    enricher: Enricher = Enricher(dataset_dir, github_service)
    collector: AddonCollector = AddonCollector(ankiweb_service, enricher, overrider)
    addon_infos: list[AddonInfo] = collector.collect_addons()

    aggregation: Aggregation = Aggregator.aggregate(addon_infos)

    exporter_facade: ExporterFacade = ExporterFacade(dataset_dir)
    exporter_facade.export_all(addon_infos, aggregation)

    dataset_metadata_file: Path = Path(__file__).parent / "dataset-metadata.json"
    dest_dataset_metadata_file: Path = dataset_dir / "dataset-metadata.json"
    shutil.copy(dataset_metadata_file, dest_dataset_metadata_file)

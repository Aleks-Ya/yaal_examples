import shutil
from pathlib import Path

from app.addon_catalog.collector.addon_collector import AddonCollector
from app.addon_catalog.collector.ankiweb.ankiweb_service import AnkiWebService
from app.addon_catalog.collector.enricher.enricher import Enricher
from app.addon_catalog.collector.github.github_service_rest import GithubServiceRest
from app.addon_catalog.collector.overrider.addon_details_overrider import AddonDetailsOverrider
from app.addon_catalog.common.data_types import AddonDetails
from app.addon_catalog.exporter.json.json_exporter import JsonExporter
from app.addon_catalog.exporter.markdown.markdown_exporter import MarkdownExporter

if __name__ == "__main__":
    working_dir: Path = Path.home() / "anki-addon-catalog"
    dataset_dir: Path = working_dir / "dataset"
    cache_dir: Path = working_dir / "cache"
    shutil.rmtree(dataset_dir)
    ankiweb_service: AnkiWebService = AnkiWebService(dataset_dir, cache_dir)
    github_service: GithubServiceRest = GithubServiceRest(dataset_dir, cache_dir)
    enricher: Enricher = Enricher(dataset_dir, github_service)
    addon_details_overrider: AddonDetailsOverrider = AddonDetailsOverrider(dataset_dir)
    collector: AddonCollector = AddonCollector(ankiweb_service, enricher, addon_details_overrider)
    details_list: list[AddonDetails] = collector.collect_addons()

    json_exporter: JsonExporter = JsonExporter(dataset_dir)
    json_exporter.export(details_list)
    markdown_exporter: MarkdownExporter = MarkdownExporter(dataset_dir)
    markdown_exporter.export(details_list)

from pathlib import Path

from app.addon_catalog.collector.addon_collector import AddonCollector
from app.addon_catalog.collector.ankiweb.ankiweb_service import AnkiWebService
from app.addon_catalog.collector.enricher.enricher import Enricher
from app.addon_catalog.collector.github.github_service_rest import GithubServiceRest
from app.addon_catalog.common.data_types import AddonDetails
from app.addon_catalog.exporter.json.json_exporter import JsonExporter
from app.addon_catalog.exporter.markdown.markdown_exporter import MarkdownExporter

if __name__ == "__main__":
    working_dir: Path = Path.home() / "anki-addon-catalog"
    cache_dir: Path = working_dir / "cache"
    ankiweb_service: AnkiWebService = AnkiWebService(cache_dir)
    github_service: GithubServiceRest = GithubServiceRest(cache_dir)
    enricher: Enricher = Enricher(github_service)
    collector: AddonCollector = AddonCollector(ankiweb_service, github_service, enricher)
    details_list: list[AddonDetails] = collector.collect_addons()

    output_dir: Path = working_dir / "catalog"
    json_exporter: JsonExporter = JsonExporter(output_dir)
    json_exporter.export(details_list)
    markdown_exporter: MarkdownExporter = MarkdownExporter(output_dir)
    markdown_exporter.export(details_list)

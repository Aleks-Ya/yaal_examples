from pathlib import Path

from app.addon_catalog.collector.addon_collector import AddonCollector
from app.addon_catalog.common.data_types import AddonDetails
from app.addon_catalog.exporter.json_exporter import JsonExporter
from app.addon_catalog.exporter.markdown_exporter import MarkdownExporter

if __name__ == "__main__":
    details_list: list[AddonDetails] = AddonCollector.collect_addons()

    addons_json_file: Path = Path.home() / "tmp" / "anki_addons_page" / "addons.json"
    JsonExporter.export(addons_json_file, details_list)

    addons_md_file: Path = Path.home() / "tmp" / "anki_addons_page" / "addons.md"
    MarkdownExporter.export(addons_md_file, details_list)

    # TODO override given parsed values with manual

from pathlib import Path

from app.addon_catalog.common.data_types import AddonInfo, Aggregation
from app.addon_catalog.exporter.exporter import Exporter
from app.addon_catalog.exporter.json.json_exporter import JsonExporter
from app.addon_catalog.exporter.markdown.markdown_exporter import MarkdownExporter
from app.addon_catalog.exporter.xlsx.xlsx_exporter import XlsxExporter


class ExporterFacade:
    def __init__(self, dataset_dir: Path):
        self.exporters: list[Exporter] = [
            JsonExporter(dataset_dir),
            MarkdownExporter(dataset_dir),
            XlsxExporter(dataset_dir)
        ]

    def export_all(self, addon_infos: list[AddonInfo], aggregation: Aggregation) -> None:
        for exporter in self.exporters:
            exporter.export_addon_infos(addon_infos)
            exporter.export_aggregation(aggregation)

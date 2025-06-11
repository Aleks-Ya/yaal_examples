from pathlib import Path

from xlsxwriter import Workbook

from app.addon_catalog.common.data_types import AddonInfo, Aggregation
from app.addon_catalog.exporter.exporter import Exporter
from app.addon_catalog.exporter.xlsx.addon_info_sheet import AddonInfoSheet
from app.addon_catalog.exporter.xlsx.aggregation_sheet import AggregationSheet


class XlsxExporter(Exporter):

    def export_addon_infos(self, addon_infos: list[AddonInfo]):
        output_file: Path = self._dataset_dir / "anki-addon-catalog.xlsx"
        workbook: Workbook = Workbook(output_file)
        AddonInfoSheet.create_sheet(workbook, addon_infos)
        workbook.close()
        print(f"Write XLSX to file: {output_file}")

    def export_aggregation(self, aggregation: Aggregation) -> None:
        output_file: Path = self._dataset_dir / "aggregation.xlsx"
        workbook: Workbook = Workbook(output_file)
        AggregationSheet.create_sheet(workbook, aggregation)
        workbook.close()
        print(f"Write XLSX to file: {output_file}")

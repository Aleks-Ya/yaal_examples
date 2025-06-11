from pathlib import Path

from mdutils import MdUtils

from app.addon_catalog.common.data_types import AddonInfo, Aggregation
from app.addon_catalog.exporter.exporter import Exporter


class MarkdownExporter(Exporter):

    def export_addon_infos(self, addon_infos: list[AddonInfo]):
        output_file: Path = self._dataset_dir / "anki-addon-catalog.md"
        md: MdUtils = MdUtils(file_name=str(output_file), title='Anki Addons Catalog for Programmers')
        md.new_line()
        lines: list[str] = ["ID", "Title", "Rating", "Stars"]
        column_number: int = len(lines)
        for addon in addon_infos:
            line: list[str] = [addon.header.id, addon.header.title, addon.header.rating, addon.stars]
            lines.extend(line)
        md.new_table(column_number, len(addon_infos) + 1, lines)
        md.create_md_file()
        print(f"Write MarkDown to file: {output_file}")

    def export_aggregation(self, aggregation: Aggregation) -> None:
        output_file: Path = self._dataset_dir / "aggregation.md"
        md: MdUtils = MdUtils(file_name=str(output_file), title='Anki Addons Catalog for Programmers')
        md.new_line(f"Addon number: {aggregation.addon_number}")
        md.create_md_file()
        print(f"Write MarkDown to file: {output_file}")

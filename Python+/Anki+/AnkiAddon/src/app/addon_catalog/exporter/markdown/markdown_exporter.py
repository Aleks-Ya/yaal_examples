from pathlib import Path

from mdutils import MdUtils

from app.addon_catalog.common.data_types import AddonDetails


class MarkdownExporter:
    def __init__(self, output_dir: Path):
        self.output_dir: Path = output_dir
        self.output_dir.mkdir(parents=True, exist_ok=True)

    def export(self, details_list: list[AddonDetails]):
        output_file: Path = self.output_dir / "addons.md"
        md: MdUtils = MdUtils(file_name=str(output_file), title='Anki Addons Catalog for Programmers')
        md.new_line()
        lines: list[str] = ["ID", "Title", "Rating", "Stars"]
        column_number: int = len(lines)
        for addon in details_list:
            line: list[str] = [addon.header.id, addon.header.title, addon.header.rating, addon.stars]
            lines.extend(line)
        md.new_table(column_number, len(details_list) + 1, lines)
        md.create_md_file()
        print(f"Write JSON to file: {output_file}")

from pathlib import Path

from mdutils import MdUtils

from app.addon_catalog.common.data_types import AddonDetails


class MarkdownExporter:
    @staticmethod
    def export(markdown_file: Path, details_list: list[AddonDetails]):
        md: MdUtils = MdUtils(file_name=str(markdown_file), title='Anki Addons Catalog for Programmers')
        md.new_line()
        lines: list[str] = ["ID", "Title", "Stars"]
        for addon in details_list:
            line: list[str] = [addon.header.id, addon.header.title, addon.header.rating]
            lines.extend(line)
        md.new_table(3, len(details_list) + 1, lines)
        md.create_md_file()
        print(f"Write JSON to file: {markdown_file}")

from pathlib import Path

from app.addon_catalog.common.data_types import AddonDetails
from app.addon_catalog.exporter.json_storage import JsonStorage


class JsonExporter:
    @staticmethod
    def export(json_file: Path, details_list: list[AddonDetails]) -> None:
        json_str: str = JsonStorage.convert_addons_to_json(details_list)
        json_file.write_text(json_str)
        print(f"Write JSON to file: {json_file}")

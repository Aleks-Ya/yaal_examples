from pathlib import Path

from app.addon_catalog.common.data_types import AddonDetails
from app.addon_catalog.exporter.json.json_storage import JsonStorage


class JsonExporter:
    def __init__(self, output_dir: Path):
        self.output_dir: Path = output_dir
        self.output_dir.mkdir(parents=True, exist_ok=True)

    def export(self, details_list: list[AddonDetails]) -> None:
        output_file: Path = self.output_dir / "addons.json"
        json_str: str = JsonStorage.convert_addons_to_json(details_list)
        output_file.write_text(json_str)
        print(f"Write JSON to file: {output_file}")

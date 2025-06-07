import dataclasses
import json
from pathlib import Path
from typing import Any

from app.addon_catalog.common.data_types import AddonDetails


class JsonHelper:

    @staticmethod
    def convert_addons_to_json(addons: list[AddonDetails]) -> str:
        dicts: list[dict[str, Any]] = [dataclasses.asdict(addon) for addon in addons]
        return json.dumps(dicts, indent=2)

    @staticmethod
    def write_addon_details_to_file(addon_details: AddonDetails, file: Path) -> None:
        content_json: str = json.dumps(dataclasses.asdict(addon_details), indent=2)
        file.write_text(content_json)

    @staticmethod
    def write_dict_to_file(content: dict[str, Any], file: Path) -> None:
        content_json: str = json.dumps(content, indent=2)
        file.write_text(content_json)

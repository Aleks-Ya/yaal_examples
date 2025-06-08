import dataclasses
import json
from pathlib import Path
from typing import Any

from app.addon_catalog.common.data_types import AddonDetails


class JsonHelper:

    @staticmethod
    def write_addon_details_to_file(addon_details: AddonDetails, file: Path) -> None:
        file.parent.mkdir(parents=True, exist_ok=True)
        content_json: str = json.dumps(dataclasses.asdict(addon_details), indent=2)
        file.write_text(content_json)

    @staticmethod
    def write_dict_to_file(content: dict[str, Any], file: Path) -> None:
        file.parent.mkdir(parents=True, exist_ok=True)
        content_json: str = json.dumps(content, indent=2)
        file.write_text(content_json)

    @staticmethod
    def write_content_to_file(content: str, file: Path) -> None:
        JsonHelper.write_dict_to_file(json.loads(content), file)

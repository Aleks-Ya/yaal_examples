import dataclasses
import json
from typing import Any

from app.addon_catalog.data_types import AddonDetails


class JsonStorage:

    @staticmethod
    def convert_addons_to_json(addons: list[AddonDetails]) -> str:
        dicts: list[dict[str, Any]] = [dataclasses.asdict(addon) for addon in addons]
        return json.dumps(dicts, indent=2)

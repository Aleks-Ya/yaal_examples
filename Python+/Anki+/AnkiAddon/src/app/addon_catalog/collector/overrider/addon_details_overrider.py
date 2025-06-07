from pathlib import Path
from typing import Any

import yaml

from app.addon_catalog.common.data_types import AddonDetails, AddonId


class AddonDetailsOverrider:
    @staticmethod
    def overwrite(details_list: list[AddonDetails]) -> list[AddonDetails]:
        override_file: Path = Path(__file__).parent / "overrides.yaml"
        print(f"Read override file: {override_file}")
        data: dict[str, dict[AddonId, Any]] = yaml.safe_load(override_file.read_text())
        addons_data: dict[AddonId, Any] = data.get("addons", {})
        for details in details_list:
            if details.header.id in addons_data:
                for key, value in addons_data[details.header.id].items():
                    setattr(details.header, key, value)
        return details_list

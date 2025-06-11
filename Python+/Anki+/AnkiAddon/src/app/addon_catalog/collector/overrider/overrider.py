import shutil
from pathlib import Path
from typing import Any, Optional

import yaml

from app.addon_catalog.collector.ankiweb.url_parser import UrlParser
from app.addon_catalog.common.data_types import AddonInfo, AddonId, URL, GitHubLink


class Overrider:
    __github_url_key: str = "GitHubUrl"
    __anki_forum_url_key: str = "AnkiForumUrl"

    def __init__(self, dataset_dir: Path):
        override_file: Path = Path(__file__).parent / "overrides.yaml"
        print(f"Read override file: {override_file}")
        data: dict[str, dict[AddonId, Any]] = yaml.safe_load(override_file.read_text())
        self.addons_data: dict[AddonId, Any] = data.get("addons", {})
        dest_file: Path = dataset_dir / "raw" / "4-overrider" / "overrides.yaml"
        dest_file.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy(override_file, dest_file)

    def override(self, addon_infos: list[AddonInfo]) -> list[AddonInfo]:
        for addon_info in addon_infos:
            if addon_info.header.id in self.addons_data:
                for key, value in self.addons_data[addon_info.header.id].items():
                    setattr(addon_info.header, key, value)
        return addon_infos

    def override_github_link(self, addon_id: AddonId) -> Optional[GitHubLink]:
        if addon_id in self.addons_data:
            addon_data: dict[str, Any] = self.addons_data[addon_id]
            if self.__github_url_key in addon_data:
                github_url: URL = addon_data[self.__github_url_key]
                return UrlParser.parse_github_url(github_url)
        return None

    def override_anki_forum_url(self, addon_id: AddonId) -> Optional[URL]:
        if addon_id in self.addons_data:
            addon_data: dict[str, Any] = self.addons_data[addon_id]
            if self.__anki_forum_url_key in addon_data:
                return addon_data[self.__anki_forum_url_key]
        return None

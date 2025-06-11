import dataclasses
import json
from dataclasses import dataclass
from pathlib import Path
from typing import Any, Optional

from app.addon_catalog.common.data_types import AddonInfo, Aggregation
from app.addon_catalog.exporter.exporter import Exporter


@dataclass
class Link:
    url: str
    user: Optional[str]
    repo: Optional[str]


@dataclass
class GitHub:
    user: Optional[str]
    repo: Optional[str]
    languages: list[str]
    stars: int
    last_commit: str
    links: list[Link]
    action_count: int
    tests_count: int


@dataclass
class Details:
    id: int
    title: str
    addon_page: str
    rating: int
    update_date: str
    versions: str
    anki_forum_url: Optional[str]
    github: Optional[GitHub]
    links: list[str]


class JsonExporter(Exporter):

    def export_addon_infos(self, addon_infos: list[AddonInfo]) -> None:
        json_list: list[Details] = []
        for addon in addon_infos:
            links: list[Link] = [Link(link.url,
                                      link.user.user_name,
                                      link.repo.repo_name if link.repo else None)
                                 for link in addon.github_links]
            last_commit_str: str = addon.last_commit.isoformat() if addon.last_commit else None
            if addon.github_repo:
                user: str = addon.github_repo.user
                repo_str: str = addon.github_repo.repo_name
                github: Optional[GitHub] = GitHub(user, repo_str, addon.languages, addon.stars, last_commit_str, links,
                                                  addon.action_count, addon.tests_count)
            else:
                github: Optional[GitHub] = None
            json_obj: Details = Details(
                addon.header.id, addon.header.title, addon.header.addon_page, addon.header.rating,
                addon.header.update_date, addon.header.versions, addon.anki_forum_url, github, addon.other_links)
            json_list.append(json_obj)
        output_file: Path = self._dataset_dir / "anki-addon-catalog.json"
        json_str: str = JsonExporter.__to_json(json_list)
        output_file.write_text(json_str)
        print(f"Write JSON to file: {output_file}")

    def export_aggregation(self, aggregation: Aggregation) -> None:
        aggregation_dict: dict[str, int] = dataclasses.asdict(aggregation)
        output_file: Path = self._dataset_dir / "aggregation.json"
        json_str: str = json.dumps(aggregation_dict, indent=2)
        output_file.write_text(json_str)
        print(f"Write JSON to file: {output_file}")

    @staticmethod
    def __to_json(addons: list[Details]) -> str:
        dicts: list[dict[str, Any]] = [dataclasses.asdict(addon) for addon in addons]
        return json.dumps(dicts, indent=2)

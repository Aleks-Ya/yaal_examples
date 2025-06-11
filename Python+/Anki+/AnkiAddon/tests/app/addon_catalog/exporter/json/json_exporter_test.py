import json
import tempfile
from datetime import datetime
from pathlib import Path
from typing import Any

from app.addon_catalog.common.data_types import AddonInfo, AddonHeader, AddonId, GitHubRepo, GithubUserName, \
    GithubRepoName, LanguageName
from app.addon_catalog.exporter.json.json_exporter import JsonExporter


def test_export(note_size_addon_id: AddonId):
    output_dir: Path = Path(tempfile.mkdtemp())
    exporter: JsonExporter = JsonExporter(output_dir)
    addon_infos: list[AddonInfo] = [
        AddonInfo(
            header=AddonHeader(note_size_addon_id, "NoteSize", "https://ankiweb.net/shared/info/1188705668",
                               4, "2023-03-15", "1.0.0"),
            github_links=[],
            other_links=[],
            github_repo=GitHubRepo(GithubUserName("John"), GithubRepoName("app")),
            languages=[LanguageName("Python"), LanguageName("Rust")],
            stars=3,
            last_commit=datetime(2023, 3, 15, 12, 0, 0, 0),
            anki_forum_url=None,
            action_count=5,
            tests_count=7)
    ]
    exporter.export_addon_infos(addon_infos)
    act_json_file: Path = output_dir / "anki-addon-catalog.json"
    act_json: dict[str, Any] = json.loads(act_json_file.read_text())
    assert act_json == [{'addon_page': 'https://ankiweb.net/shared/info/1188705668',
                         'anki_forum_url': None,
                         'github': {'action_count': 5,
                                    'languages': ['Python', 'Rust'],
                                    'last_commit': '2023-03-15T12:00:00',
                                    'links': [],
                                    'repo': 'app',
                                    'stars': 3,
                                    'tests_count': 7,
                                    'user': 'John'},
                         'id': 1188705668,
                         'links': [],
                         'rating': 4,
                         'title': 'NoteSize',
                         'update_date': '2023-03-15',
                         'versions': '1.0.0'}]

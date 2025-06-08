import json
import tempfile
from datetime import datetime
from pathlib import Path
from typing import Any

from app.addon_catalog.common.data_types import AddonDetails, AddonHeader, AddonId
from app.addon_catalog.exporter.json.json_exporter import JsonExporter


def test_export():
    output_dir: Path = Path(tempfile.mkdtemp())
    exporter: JsonExporter = JsonExporter(output_dir)
    addons_list: list[AddonDetails] = [
        AddonDetails(
            header=AddonHeader(AddonId(1188705668), "NoteSize", "https://ankiweb.net/shared/info/1188705668",
                               4, "2023-03-15", "1.0.0"),
            github_links=[],
            other_links=[],
            github_repo=None,
            languages=[],
            stars=0,
            last_commit=datetime(2023, 3, 15, 12, 0, 0, 0),
            anki_forum_url=None),
    ]
    exporter.export(addons_list)
    act_json_file: Path = output_dir / "anki-addon-catalog.json"
    act_json: dict[str, Any] = json.loads(act_json_file.read_text())
    assert act_json == [{'addon_page': 'https://ankiweb.net/shared/info/1188705668',
                         'anki_forum_url': None,
                         'github': {'languages': [],
                                    'last_commit': '2023-03-15T12:00:00',
                                    'links': [],
                                    'repo': None,
                                    'stars': 0,
                                    'user': None},
                         'id': 1188705668,
                         'links': [],
                         'rating': 4,
                         'title': 'NoteSize',
                         'update_date': '2023-03-15',
                         'versions': '1.0.0'}]

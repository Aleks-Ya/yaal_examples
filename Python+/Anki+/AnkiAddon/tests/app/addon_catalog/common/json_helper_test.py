from datetime import datetime
from textwrap import dedent

from app.addon_catalog.common.data_types import AddonDetails, AddonHeader, AddonId
from app.addon_catalog.common.json_helper import JsonHelper


def test_convert_addons_to_json():
    addons_list: list[AddonDetails] = [
        AddonDetails(
            header=AddonHeader(AddonId(1188705668), "NoteSize", "https://ankiweb.net/shared/info/1188705668",
                               "4.5", "2023-03-15", "1.0.0"),
            github_links=[],
            other_links=[],
            github_repo=None,
            languages=[],
            stars=0,
            last_commit=datetime(2023, 3, 15, 12, 0, 0, 0)),
    ]
    json_str: str = JsonHelper.convert_addons_to_json(addons_list)
    assert json_str == dedent("""\
    [
      {
        "header": {
          "id": 1188705668,
          "title": "NoteSize",
          "addon_page": "https://ankiweb.net/shared/info/1188705668",
          "rating": "4.5",
          "update_date": "2023-03-15",
          "versions": "1.0.0"
        },
        "github_links": [],
        "other_links": [],
        "github_repo": null,
        "languages": [],
        "stars": 0,
        "last_commit": "2023-03-15T12:00:00"
      }
    ]""")

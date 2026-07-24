import json
import os
from pathlib import Path

from apps.libre_office_draw_search.data_types import OdgPath, PageName, Text
from apps.libre_office_draw_search.odg_parser import OdgFileData, OdgParser


def _default_index_path() -> Path:
    cache_home: Path = Path(os.environ.get("XDG_CACHE_HOME", Path.home() / ".cache"))
    return cache_home / "libre_office_draw_search" / "index.json"


class ParseCache:
    """Caches OdgParser results on disk, keyed by file path + modification time.

    Unchanged files are served from a JSON index instead of being re-parsed. Only files queried in the
    current run are kept, so deletions are pruned on the next save().
    """

    def __init__(self, index_path: Path | None = None):
        self.__index_path: Path = index_path if index_path is not None else _default_index_path()
        self.__stored: dict[str, dict] = self.__load()
        self.__fresh: dict[str, dict] = {}

    def get_or_parse(self, file: OdgPath) -> OdgFileData:
        key: str = str(file)
        mtime: float = file.stat().st_mtime
        entry: dict | None = self.__fresh.get(key) or self.__stored.get(key)
        if entry is not None and entry.get("mtime") == mtime:
            data: OdgFileData = OdgFileData(
                [PageName(page_name) for page_name in entry["page_names"]],
                [Text(text) for text in entry["texts"]],
            )
        else:
            data = OdgParser.parse(file)
        self.__fresh[key] = {"mtime": mtime, "page_names": data.page_names, "texts": data.texts}
        return data

    def save(self) -> None:
        self.__index_path.parent.mkdir(parents=True, exist_ok=True)
        self.__index_path.write_text(json.dumps(self.__fresh))

    def __load(self) -> dict[str, dict]:
        try:
            return json.loads(self.__index_path.read_text())
        except (FileNotFoundError, json.JSONDecodeError):
            return {}

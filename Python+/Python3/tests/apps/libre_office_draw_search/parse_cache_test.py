import os
from pathlib import Path

import pytest

from apps.libre_office_draw_search import parse_cache as parse_cache_module
from apps.libre_office_draw_search.data_types import OdgPath
from apps.libre_office_draw_search.odg_parser import OdgFileData, OdgParser
from apps.libre_office_draw_search.parse_cache import ParseCache


@pytest.fixture
def index_path(tmp_path: Path) -> Path:
    return tmp_path / "index.json"


@pytest.fixture
def parse_spy(monkeypatch: pytest.MonkeyPatch) -> list[OdgPath]:
    """Records every OdgParser.parse call while keeping real parsing behaviour."""
    calls: list[OdgPath] = []
    real_parse = OdgParser.parse

    def spy(odg_file: OdgPath) -> OdgFileData:
        calls.append(odg_file)
        return real_parse(odg_file)

    monkeypatch.setattr(OdgParser, "parse", staticmethod(spy))
    return calls


def test_first_call_parses(index_path: Path, real_buildings_file: OdgPath, parse_spy: list[OdgPath]):
    cache: ParseCache = ParseCache(index_path)
    data: OdgFileData = cache.get_or_parse(real_buildings_file)
    assert parse_spy == [real_buildings_file]
    assert "House" in data.texts


def test_second_call_uses_cache(index_path: Path, real_buildings_file: OdgPath, parse_spy: list[OdgPath]):
    cache: ParseCache = ParseCache(index_path)
    first: OdgFileData = cache.get_or_parse(real_buildings_file)
    second: OdgFileData = cache.get_or_parse(real_buildings_file)
    assert parse_spy == [real_buildings_file]
    assert first == second


def test_changed_mtime_reparses(tmp_path: Path, index_path: Path, real_buildings_file: OdgPath,
                                parse_spy: list[OdgPath]):
    copy: OdgPath = OdgPath(tmp_path / "buildings.odg")
    copy.write_bytes(real_buildings_file.read_bytes())
    cache: ParseCache = ParseCache(index_path)
    cache.get_or_parse(copy)
    os.utime(copy, (copy.stat().st_atime, copy.stat().st_mtime + 10))
    cache.get_or_parse(copy)
    assert parse_spy == [copy, copy]


def test_persisted_index_reused_by_new_instance(index_path: Path, real_buildings_file: OdgPath,
                                                parse_spy: list[OdgPath]):
    first_cache: ParseCache = ParseCache(index_path)
    expected: OdgFileData = first_cache.get_or_parse(real_buildings_file)
    first_cache.save()

    reloaded_cache: ParseCache = ParseCache(index_path)
    reloaded: OdgFileData = reloaded_cache.get_or_parse(real_buildings_file)
    assert parse_spy == [real_buildings_file]  # second instance did not re-parse
    assert reloaded == expected


def test_save_prunes_files_not_queried(index_path: Path, real_buildings_file: OdgPath,
                                       real_odg_parser_file: OdgPath):
    first_cache: ParseCache = ParseCache(index_path)
    first_cache.get_or_parse(real_buildings_file)
    first_cache.get_or_parse(real_odg_parser_file)
    first_cache.save()

    second_cache: ParseCache = ParseCache(index_path)
    second_cache.get_or_parse(real_buildings_file)  # only query one file this run
    second_cache.save()

    third_cache: ParseCache = ParseCache(index_path)
    stored: dict = third_cache._ParseCache__stored
    assert list(stored.keys()) == [str(real_buildings_file)]


def test_missing_index_loads_empty(index_path: Path):
    assert not index_path.exists()
    cache: ParseCache = ParseCache(index_path)
    assert cache._ParseCache__stored == {}


def test_corrupt_index_loads_empty(index_path: Path):
    index_path.write_text("{ not valid json")
    cache: ParseCache = ParseCache(index_path)
    assert cache._ParseCache__stored == {}


def test_default_index_path_uses_xdg_cache(monkeypatch: pytest.MonkeyPatch, tmp_path: Path):
    monkeypatch.setenv("XDG_CACHE_HOME", str(tmp_path))
    assert parse_cache_module._default_index_path() == tmp_path / "libre_office_draw_search" / "index.json"

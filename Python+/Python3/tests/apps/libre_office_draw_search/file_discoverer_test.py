from pathlib import Path

from apps.libre_office_draw_search.data_types import OdgPath
from apps.libre_office_draw_search.file_discoverer import FileDiscoverer


def test_discover(real_root_dir: OdgPath, real_buildings_file: OdgPath, real_odg_parser_file: OdgPath):
    files: list[OdgPath] = FileDiscoverer.find_draw_files(real_root_dir)
    assert files == [real_odg_parser_file, real_buildings_file]


def test_is_root_available_with_odg_files(real_root_dir: OdgPath):
    assert FileDiscoverer.is_root_available(real_root_dir) is True


def test_is_root_available_missing(tmp_path: Path):
    assert FileDiscoverer.is_root_available(tmp_path / "missing") is False


def test_is_root_available_empty_dir(tmp_path: Path):
    assert FileDiscoverer.is_root_available(tmp_path) is False


def test_is_root_available_not_a_dir(tmp_path: Path):
    file_path: Path = tmp_path / "file.txt"
    file_path.write_text("x")
    assert FileDiscoverer.is_root_available(file_path) is False


def test_is_root_available_unmounted_skeleton(tmp_path: Path):
    # Unmounted vault leftover: nested empty dirs + a LibreOffice lock file, no real .odg
    nested: Path = tmp_path / "Programming" / "Python"
    nested.mkdir(parents=True)
    (nested / ".~lock.Python Environment.odg#").write_text("lock")
    assert FileDiscoverer.is_root_available(tmp_path) is False

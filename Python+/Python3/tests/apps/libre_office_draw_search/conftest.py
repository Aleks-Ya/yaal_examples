from pathlib import Path

import pytest

from apps.libre_office_draw_search.data_types import OdgPath, SearchResult, FolderName, FileName, PageName


@pytest.fixture
def root_dir() -> Path:
    return Path("/tmp/libre")


@pytest.fixture
def real_root_dir() -> OdgPath:
    return OdgPath(Path(__file__).parent / "files")


@pytest.fixture
def real_buildings_file(real_root_dir: OdgPath) -> OdgPath:
    return OdgPath(real_root_dir / "nested" / "buildings.odg")


@pytest.fixture
def real_odg_parser_file(real_root_dir: OdgPath) -> OdgPath:
    return OdgPath(real_root_dir / "odg_parser_test.odg")


@pytest.fixture
def search_result_1(root_dir: Path) -> SearchResult:
    return SearchResult(1, OdgPath(root_dir / 'dir1' / 'file1.odg'), [FolderName('dir1')], [FileName("file1.odg")],
                        [PageName("Page 1")], [])


@pytest.fixture
def search_result_2(root_dir: Path) -> SearchResult:
    return SearchResult(2, OdgPath(root_dir / 'dir1' / 'file2.odg'), [], [FileName("file2.odg")],
                        [PageName("Page 1"), PageName("Page 2")], [])

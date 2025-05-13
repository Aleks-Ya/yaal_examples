from pathlib import Path

import pytest

from apps.libre_office_draw_search.data_types import FodgPath, SearchResult, FolderName, FileName, PageName


@pytest.fixture
def root_dir() -> Path:
    return Path("/tmp/libre")


@pytest.fixture
def real_root_dir() -> FodgPath:
    return FodgPath(Path(__file__).parent / "files")


@pytest.fixture
def real_buildings_file(real_root_dir: FodgPath) -> FodgPath:
    return FodgPath(real_root_dir / "nested" / "buildings.fodg")


@pytest.fixture
def real_fodg_parser_file(real_root_dir: FodgPath) -> FodgPath:
    return FodgPath(real_root_dir / "fodg_parser_test.fodg")


@pytest.fixture
def search_result_1(root_dir: Path) -> SearchResult:
    return SearchResult(1, FodgPath(root_dir / 'dir1' / 'file1.fodg'), [FolderName('dir1')], [FileName("file1.fodg")],
                        [PageName("Page 1")], [])


@pytest.fixture
def search_result_2(root_dir: Path) -> SearchResult:
    return SearchResult(2, FodgPath(root_dir / 'dir1' / 'file2.fodg'), [], [FileName("file2.fodg")],
                        [PageName("Page 1"), PageName("Page 2")], [])

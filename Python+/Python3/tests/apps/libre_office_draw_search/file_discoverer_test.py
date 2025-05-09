from pathlib import Path

from apps.libre_office_draw_search.data_types import FodgPath
from apps.libre_office_draw_search.file_discoverer import FileDiscoverer


def test_discover():
    root_dir: FodgPath = FodgPath(Path(__file__).parent / "files")
    files: list[FodgPath] = FileDiscoverer.find_draw_files(root_dir)
    assert files == [
        root_dir / "buildings.fodg",
        root_dir / "fodg_parser_test.fodg",
    ]
